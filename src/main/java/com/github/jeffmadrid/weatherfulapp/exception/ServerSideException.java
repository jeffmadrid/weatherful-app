package com.github.jeffmadrid.weatherfulapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServerSideException extends AppException {

    public ServerSideException(String message) {
        super(message);
    }

    public ServerSideException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideException(Throwable cause) {
        super(cause);
    }
}
