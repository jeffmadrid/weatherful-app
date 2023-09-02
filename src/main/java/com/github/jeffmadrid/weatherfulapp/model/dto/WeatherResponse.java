package com.github.jeffmadrid.weatherfulapp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(
    @JsonProperty("coord")
    Coordinates coordinates,
    @JsonProperty("weather")
    List<Weather> weathers,
    String base,
    Main main,
    Integer visibility,
    Wind wind,
    Rain rain,
    Snow snow,
    @JsonProperty("clouds")
    Cloud clouds,
    Long dt,
    Sys sys,
    Integer timezone,
    Long id,
    String name,
    Integer cod
) {

}


