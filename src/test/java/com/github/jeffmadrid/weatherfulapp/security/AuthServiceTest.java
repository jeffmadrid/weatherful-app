package com.github.jeffmadrid.weatherfulapp.security;

import com.github.jeffmadrid.weatherfulapp.model.entity.tokens.TokenEntity;
import com.github.jeffmadrid.weatherfulapp.repository.TokenAccessRepository;
import com.github.jeffmadrid.weatherfulapp.repository.TokenRepository;
import com.github.jeffmadrid.weatherfulapp.stubs.TokenEntityStubs;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private static final int RATE_LIMIT_CAPACITY = 3;
    private static final int RATE_LIMIT_DURATION_IN_MINUTES = 1;

    @InjectMocks
    AuthService authService;

    @Mock
    TokenRepository tokenRepository;

    @Mock
    TokenAccessRepository tokenAccessRepository;

    @Mock
    HttpServletRequest httpServletRequest;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(authService, "rateLimitCapacity", RATE_LIMIT_CAPACITY);
        ReflectionTestUtils.setField(authService, "rateLimitDurationInMinutes", RATE_LIMIT_DURATION_IN_MINUTES);
    }

    @Test
    void testSuccessfulGetAuthentication() {
        TokenEntity entity = TokenEntityStubs.stubTokenEntity();
        when(httpServletRequest.getHeader(any())).thenReturn(entity.getToken());
        when(tokenRepository.findById(any())).thenReturn(Optional.of(entity));

        Authentication authentication = authService.getAuthentication(httpServletRequest);

        assertThat(authentication.getPrincipal()).isEqualTo(entity.getToken());
        assertThat(authentication).isInstanceOf(ApiKeyAuthentication.class);

        verify(httpServletRequest, times(1)).getHeader(any());
        verify(tokenRepository, times(1)).findById(any());
    }

    @Test
    void testGetAuthenticationNoApiKey() {
        when(httpServletRequest.getHeader(any())).thenReturn(null);

        Authentication authentication = authService.getAuthentication(httpServletRequest);

        assertThat(authentication).isNull();

        verify(httpServletRequest, times(1)).getHeader(any());
        verify(tokenRepository, never()).findById(any());
    }

    @Test
    void testGetAuthenticationInvalidApiKey() {
        when(httpServletRequest.getHeader(any())).thenReturn("test-api-key");
        when(tokenRepository.findById(any())).thenReturn(Optional.empty());

        Authentication authentication = authService.getAuthentication(httpServletRequest);

        assertThat(authentication).isNull();

        verify(httpServletRequest, times(1)).getHeader(any());
        verify(tokenRepository, times(1)).findById(any());
    }

    @Test
    void testSuccessfulCheckAndPerformAuthorization() {
        when(httpServletRequest.getHeader(any())).thenReturn("test-api-key");
        when(tokenAccessRepository.countByTokenAndAccessedDateAfter(any(), any()))
            .thenReturn(RATE_LIMIT_CAPACITY - 1);

        boolean result = authService.checkAndPerformAuthorization(httpServletRequest);

        assertThat(result).isTrue();

        verify(httpServletRequest, times(1)).getHeader(any());
        verify(tokenAccessRepository, times(1)).countByTokenAndAccessedDateAfter(any(), any());
        verify(tokenAccessRepository, times(1)).save(any());
    }

    @Test
    void testCheckAndPerformAuthorizationNoApiKey() {
        when(httpServletRequest.getHeader(any())).thenReturn(null);

        boolean result = authService.checkAndPerformAuthorization(httpServletRequest);

        assertThat(result).isFalse();

        verify(httpServletRequest, times(1)).getHeader(any());
        verify(tokenAccessRepository, never()).countByTokenAndAccessedDateAfter(any(), any());
        verify(tokenAccessRepository, never()).save(any());
    }

    @Test
    void testCheckAndPerformAuthorizationRateLimitReached() {
        when(httpServletRequest.getHeader(any())).thenReturn("test-api-key");
        when(tokenAccessRepository.countByTokenAndAccessedDateAfter(any(), any()))
            .thenReturn(RATE_LIMIT_CAPACITY);

        boolean result = authService.checkAndPerformAuthorization(httpServletRequest);

        assertThat(result).isFalse();

        verify(httpServletRequest, times(1)).getHeader(any());
        verify(tokenAccessRepository, times(1)).countByTokenAndAccessedDateAfter(any(), any());
        verify(tokenAccessRepository, never()).save(any());
    }
}
