package com.github.jeffmadrid.weatherfulapp.model.api;

public record WeatherfulResponse(
    String city,
    String country,
    Weather weather
) {
}
