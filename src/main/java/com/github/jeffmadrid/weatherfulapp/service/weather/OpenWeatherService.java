package com.github.jeffmadrid.weatherfulapp.service.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jeffmadrid.weatherfulapp.exception.CityNotFoundException;
import com.github.jeffmadrid.weatherfulapp.exception.ServerSideException;
import com.github.jeffmadrid.weatherfulapp.mapper.WeatherMapper;
import com.github.jeffmadrid.weatherfulapp.model.api.WeatherfulResponse;
import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;
import com.github.jeffmadrid.weatherfulapp.model.entity.WeatherDataEntity;
import com.github.jeffmadrid.weatherfulapp.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.OffsetDateTime;

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

    @Value("${open-weather-map.data.fresh-data-duration-in-minutes}")
    private int freshDataDuration;

    @Transactional
    @Override
    public WeatherfulResponse getWeather(String city, String country) {
        WeatherResponse weather = weatherDataRepository.findByCityIgnoreCaseAndCountryIgnoreCase(city, country)
            .filter(this::isDataFresh)
            .map(this::mapToWeatherResponse)
            .orElseGet(() -> getWeatherFromExternalAndSaveToDb(city, country));

        return weatherMapper.toApiResponse(weather);
    }

    private boolean isDataFresh(WeatherDataEntity entity) {
        return entity.getUpdatedDate().plusMinutes(freshDataDuration).isAfter(OffsetDateTime.now());
    }

    private WeatherResponse mapToWeatherResponse(WeatherDataEntity entity) {
        try {
            return objectMapper.readValue(entity.getRawData(), WeatherResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse entity in db with city " + entity.getCity() + " country " + entity.getCountry());
            return null;
        }
    }

    private WeatherResponse getWeatherFromExternalAndSaveToDb(String city, String country) {
        try {
            String queryParam = city + ',' + country;
            WeatherResponse weather = openWeatherApiClient.getWeatherByCityAndCountry(queryParam, apiKey);
            log.info("Successfully retrieved data from OpenWeatherData for city " + city + " country " + country);
            weatherDataRepository.save(weatherMapper.toEntity(weather));
            return weather;
        } catch (WebClientResponseException.NotFound e) {
            throw new CityNotFoundException("Data not found for the city " + city + " country " + country);
        } catch (WebClientResponseException e) {
            throw new ServerSideException("Unexpected error occurred while calling external API", e);
        }
    }
}
