package com.github.jeffmadrid.weatherfulapp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Main(
    Double temp,
    Double feelsLike,
    Double tempMin,
    Double tempMax,
    Integer pressure,
    Integer humidity,
    Integer seaLevel,
    Integer grndLevel

) {
}
