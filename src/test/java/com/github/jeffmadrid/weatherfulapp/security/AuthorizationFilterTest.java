package com.github.jeffmadrid.weatherfulapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationFilterTest {

    @InjectMocks
    AuthorizationFilter authorizationFilter;

    @Mock
    AuthService authService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Test
    void testDoFilterInternalAuthorization() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/v1/weather");
        when(authService.checkAndPerformAuthorization(any())).thenReturn(true);

        authorizationFilter.doFilterInternal(request, response, filterChain);

        verify(authService, times(1)).checkAndPerformAuthorization(any());
    }

    @Test
    void testDoFilterInternalIgnoresOtherPaths() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/h2-console");

        authorizationFilter.doFilterInternal(request, response, filterChain);

        verify(authService, never()).checkAndPerformAuthorization(any());
    }
}
