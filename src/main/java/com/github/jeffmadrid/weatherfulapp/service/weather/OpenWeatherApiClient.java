package com.github.jeffmadrid.weatherfulapp.service.weather;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface OpenWeatherApiClient {
    @GetExchange("/data/2.5/weather")
    JsonNode getWeatherByCityAndCountry(@RequestParam("q") String cityAndCountry,
                                        @RequestParam("appid") String apiKey);
}
