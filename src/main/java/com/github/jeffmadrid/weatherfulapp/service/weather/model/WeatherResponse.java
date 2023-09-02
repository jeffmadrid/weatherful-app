package com.github.jeffmadrid.weatherfulapp.service.weather.model;

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
    int visibility,
    Wind wind,
    Rain rain,
    Snow snow,
    @JsonProperty("clouds")
    Cloud clouds,
    long dt,
    Sys sys,
    int timezone,
    long id,
    String name,
    int cod
) {

}


