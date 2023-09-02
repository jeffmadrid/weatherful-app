package com.github.jeffmadrid.weatherfulapp.service.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Weather(
    int id,
    String main,
    String description,
    String icon
) {
}
