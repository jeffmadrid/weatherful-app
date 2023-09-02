package com.github.jeffmadrid.weatherfulapp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Rain(
    @JsonProperty("1h")
    Double hourOne,
    @JsonProperty("3h")
    Double hourThree
) {
}
