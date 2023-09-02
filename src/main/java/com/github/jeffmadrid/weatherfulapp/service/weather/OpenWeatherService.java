package com.github.jeffmadrid.weatherfulapp.service.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jeffmadrid.weatherfulapp.exception.ClientSideException;
import com.github.jeffmadrid.weatherfulapp.exception.ServerSideException;
import com.github.jeffmadrid.weatherfulapp.mapper.WeatherMapper;
import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;
import com.github.jeffmadrid.weatherfulapp.model.entity.WeatherDataId;
import com.github.jeffmadrid.weatherfulapp.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpenWeatherService implements WeatherService {

    private final OpenWeatherApiClient openWeatherApiClient;
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherMapper weatherMapper;
    private final ObjectMapper objectMapper;

    @Value("${open-weather-map.data.api-key}")
    private String apiKey;

    @Transactional
    @Override
    public WeatherResponse getWeather(String city, String country) {
        WeatherResponse weatherFromDb = getWeatherFromDb(city, country).orElse(null);
        if (weatherFromDb != null) {
            log.info("Successfully retrieved data from db for city {} country {}", city, country);
            return weatherFromDb;
        }

        WeatherResponse weather = getWeatherFromExternal(city, country);
        log.info("Successfully retrieved data from external for city {} country {}", city, country);
        saveWeatherToDb(weather);
        return weather;
    }

    private Optional<WeatherResponse> getWeatherFromDb(String city, String country) {
        return weatherDataRepository.findByCityAndCountryIgnoreCase(city, country)
            .map(x -> objectMapper.convertValue(x.getRawData(), WeatherResponse.class));
    }

    private WeatherResponse getWeatherFromExternal(String city, String country) {
        try {
            String cityCountryQueryParam = city + ',' + country;
            return openWeatherApiClient.getWeatherByCityAndCountry(cityCountryQueryParam, apiKey);
        } catch (WebClientResponseException.NotFound e) {
            throw new ClientSideException("Weather not found from external service for city " + city +
                " country " + country);
        } catch (WebClientResponseException e) {
            throw new ServerSideException("Unexpected error occurred while calling external API", e);
        }
    }

    private void saveWeatherToDb(WeatherResponse weatherResponse) {
        weatherDataRepository.save(weatherMapper.toEntity(weatherResponse));
    }

}
