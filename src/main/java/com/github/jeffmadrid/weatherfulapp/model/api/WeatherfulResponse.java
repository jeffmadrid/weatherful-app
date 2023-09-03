package com.github.jeffmadrid.weatherfulapp.model.api;

import lombok.Builder;

@Builder
public record WeatherfulResponse(
    String city,
    String country,
    String weatherDescription
) {
}
