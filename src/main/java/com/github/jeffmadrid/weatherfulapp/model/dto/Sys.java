package com.github.jeffmadrid.weatherfulapp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Sys(
    Integer type,
    Long id,
    String country,
    Long sunrise,
    Long sunset
) {
}
