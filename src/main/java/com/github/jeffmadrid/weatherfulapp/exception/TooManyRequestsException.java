package com.github.jeffmadrid.weatherfulapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TooManyRequestsException extends ClientSideException {

    public TooManyRequestsException(String message) {
        super(message);
    }

    public TooManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyRequestsException(Throwable cause) {
        super(cause);
    }
}
