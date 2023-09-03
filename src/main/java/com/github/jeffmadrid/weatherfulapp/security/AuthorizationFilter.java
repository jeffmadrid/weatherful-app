package com.github.jeffmadrid.weatherfulapp.security;

import com.github.jeffmadrid.weatherfulapp.Constants;
import com.github.jeffmadrid.weatherfulapp.exception.TooManyRequestsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final HandlerExceptionResolver resolver;

    public AuthorizationFilter(AuthService authService,
                               @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.authService = authService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isPathRequestMatch(request) && !authService.checkAndPerformAuthorization(request)) {
            Exception e = new TooManyRequestsException("Rate limit reached. Too many requests sent.");
            resolver.resolveException(request, response, null, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPathRequestMatch(HttpServletRequest request) {
        return Constants.V1_ANT_PATH_REQUEST_MATCHER.matches(request);
    }
}
