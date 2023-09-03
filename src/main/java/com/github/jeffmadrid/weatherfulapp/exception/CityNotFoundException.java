package com.github.jeffmadrid.weatherfulapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CityNotFoundException extends ClientSideException {

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityNotFoundException(Throwable cause) {
        super(cause);
    }
}
