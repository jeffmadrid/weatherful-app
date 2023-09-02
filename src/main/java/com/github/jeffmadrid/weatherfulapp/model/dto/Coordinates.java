package com.github.jeffmadrid.weatherfulapp.model.dto;

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
