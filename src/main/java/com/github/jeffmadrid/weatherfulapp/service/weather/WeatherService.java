package com.github.jeffmadrid.weatherfulapp.service.weather;

import com.github.jeffmadrid.weatherfulapp.model.api.WeatherfulResponse;

public interface WeatherService {
    WeatherfulResponse getWeather(String city, String country);
}
