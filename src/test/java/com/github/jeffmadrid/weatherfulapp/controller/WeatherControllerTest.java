package com.github.jeffmadrid.weatherfulapp.controller;

import com.github.jeffmadrid.weatherfulapp.service.weather.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

    @InjectMocks
    WeatherController weatherController;

    @Mock
    WeatherService weatherService;

    @Test
    void testController() {
        weatherController.getWeather("city", "co");

        verify(weatherService, atMostOnce()).getWeather(any(), any());
    }
}
