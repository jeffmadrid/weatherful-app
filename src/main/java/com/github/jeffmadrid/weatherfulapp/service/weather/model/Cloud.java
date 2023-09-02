package com.github.jeffmadrid.weatherfulapp.service.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Cloud(
    Integer all
) {
}
