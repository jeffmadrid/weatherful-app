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
class AuthenticationFilterTest {

    @InjectMocks
    AuthenticationFilter authenticationFilter;

    @Mock
    AuthService authService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Test
    void testDoFilterInternalSuccessfulAuthentication() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/v1/weather");

        authenticationFilter.doFilterInternal(request, response, filterChain);

        verify(authService, times(1)).getAuthentication(any());
    }

    @Test
    void testDoFilterInternalIgnoresOtherPaths() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/h2-console");

        authenticationFilter.doFilterInternal(request, response, filterChain);

        verify(authService, never()).getAuthentication(any());
    }
}
