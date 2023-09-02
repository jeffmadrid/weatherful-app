package com.github.jeffmadrid.weatherfulapp.service.weather;

import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface OpenWeatherApiClient {
    @GetExchange("/data/2.5/weather")
    WeatherResponse getWeatherByCityAndCountry(@RequestParam("q") String cityAndCountry,
                                               @RequestParam("appid") String apiKey);
}
