package com.github.jeffmadrid.weatherfulapp.service.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Coordinates(
    @JsonProperty("lon")
    double longitude,
    @JsonProperty("lat")
    double latitude
) {

}
