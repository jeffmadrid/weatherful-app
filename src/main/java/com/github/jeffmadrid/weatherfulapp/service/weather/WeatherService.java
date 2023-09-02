package com.github.jeffmadrid.weatherfulapp.service.weather;

import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeather(String city, String country);
}
