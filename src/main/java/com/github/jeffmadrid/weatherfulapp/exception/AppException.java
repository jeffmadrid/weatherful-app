package com.github.jeffmadrid.weatherfulapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}
