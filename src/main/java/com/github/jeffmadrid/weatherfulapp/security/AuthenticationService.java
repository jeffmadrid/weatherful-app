package com.github.jeffmadrid.weatherfulapp.security;

import com.github.jeffmadrid.weatherfulapp.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

public class AuthenticationService {

    private static final List<String> PERMITTED_API_KEY = List.of("AK001", "AK002", "AK003", "AK004", "AK005");

    public static Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(Constants.API_KEY_HEADER);
        if (apiKey == null || !PERMITTED_API_KEY.contains(apiKey)) {
            throw new BadCredentialsException("Invalid, empty or null API Key");
        }
        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }

}
