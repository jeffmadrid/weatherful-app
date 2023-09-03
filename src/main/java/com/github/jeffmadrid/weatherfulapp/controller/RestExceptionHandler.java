package com.github.jeffmadrid.weatherfulapp.controller;

import com.github.jeffmadrid.weatherfulapp.exception.CityNotFoundException;
import com.github.jeffmadrid.weatherfulapp.exception.ClientSideException;
import com.github.jeffmadrid.weatherfulapp.exception.ServerSideException;
import com.github.jeffmadrid.weatherfulapp.exception.TooManyRequestsException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ProblemDetail handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info("Expected exception {} is handled. {}", e.getClass(), e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    ProblemDetail handleValidationException(ValidationException e) {
        log.info("Expected exception {} is handled. {}", e.getClass(), e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(CityNotFoundException.class)
    ProblemDetail handleCityNotFoundException(CityNotFoundException e) {
        log.info("Expected exception {} is handled. {}", e.getClass(), e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(TooManyRequestsException.class)
    ProblemDetail handleTooManyRequestsException(TooManyRequestsException e) {
        log.info("Expected exception {} is handled. {}", e.getClass(), e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.TOO_MANY_REQUESTS, e.getMessage());
    }

    @ExceptionHandler(ClientSideException.class)
    ProblemDetail handleClientSideException(ClientSideException e) {
        log.info("Expected exception {} is handled. {}", e.getClass(), e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({Exception.class, ServerSideException.class})
    ProblemDetail handleException(Exception e) {
        log.error("Unexpected exception {} occurred. {}", e.getClass(), e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
