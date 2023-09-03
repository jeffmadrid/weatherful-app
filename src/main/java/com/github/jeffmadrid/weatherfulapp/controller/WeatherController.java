package com.github.jeffmadrid.weatherfulapp.controller;

import com.github.jeffmadrid.weatherfulapp.Constants;
import com.github.jeffmadrid.weatherfulapp.exception.TooManyRequestsException;
import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;
import com.github.jeffmadrid.weatherfulapp.ratelimit.RateLimitService;
import com.github.jeffmadrid.weatherfulapp.service.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/weather")
public class WeatherController {

    private final RateLimitService rateLimitService;
    private final WeatherService weatherService;

    @GetMapping
    public WeatherResponse getWeather(@RequestHeader(Constants.API_KEY_HEADER) String apiKey, @RequestParam String city,
                                      @RequestParam String country) {
        if (rateLimitService.resolveBucket(apiKey).tryConsume(1)) {
            return weatherService.getWeather(city, country);
        }
        throw new TooManyRequestsException("Too many requests sent by user");
    }
}