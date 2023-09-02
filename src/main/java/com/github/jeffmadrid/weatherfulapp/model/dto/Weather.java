package com.github.jeffmadrid.weatherfulapp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Weather(
    Integer id,
    String main,
    String description,
    String icon
) {
}
