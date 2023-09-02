package com.github.jeffmadrid.weatherfulapp.service.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Snow(
    @JsonProperty("1h")
    Double hourOne,
    @JsonProperty("3h")
    Double hourThree
) {
}
