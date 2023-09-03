package com.github.jeffmadrid.weatherfulapp.security;

import com.github.jeffmadrid.weatherfulapp.Constants;
import com.github.jeffmadrid.weatherfulapp.model.entity.tokens.TokenAccessEntity;
import com.github.jeffmadrid.weatherfulapp.repository.TokenAccessRepository;
import com.github.jeffmadrid.weatherfulapp.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final TokenAccessRepository tokenAccessRepository;

    @Value("${rate-limit.capacity}")
    private int rateLimitCapacity;

    @Value("${rate-limit.duration-in-minutes}")
    private int rateLimitDurationInMinutes;

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(Constants.API_KEY_HEADER);

        if (apiKey == null || tokenRepository.findById(apiKey).isEmpty()) {
            log.info("Invalid, empty or null API Key");
            return null;
        }
        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

    public boolean checkAndPerformAuthorization(HttpServletRequest request) {
        String apiKey = request.getHeader(Constants.API_KEY_HEADER);
        if (apiKey == null) {
            return false;
        }

        int attemptCount = tokenAccessRepository.countByTokenAndAccessedDateAfter(apiKey,
            OffsetDateTime.now().minusMinutes(rateLimitDurationInMinutes));

        if (attemptCount >= rateLimitCapacity) {
            log.info("Too many requests sent.");
            return false;
        }

        tokenAccessRepository.save(new TokenAccessEntity(apiKey, OffsetDateTime.now()));
        return true;
    }
}
