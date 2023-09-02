package com.github.jeffmadrid.weatherfulapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientSideException extends AppException {

    public ClientSideException(String message) {
        super(message);
    }

    public ClientSideException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientSideException(Throwable cause) {
        super(cause);
    }
}
