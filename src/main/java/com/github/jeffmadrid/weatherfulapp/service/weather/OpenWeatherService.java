package com.github.jeffmadrid.weatherfulapp.service.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jeffmadrid.weatherfulapp.exception.CityNotFoundException;
import com.github.jeffmadrid.weatherfulapp.exception.ServerSideException;
import com.github.jeffmadrid.weatherfulapp.model.api.WeatherfulResponse;
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

    private static final String ID_PATH = "id";
    private static final String WEATHER_PATH = "weather";
    private static final String DESCRIPTION_PATH = "description";

    @Value("${open-weather-map.data.api-key}")
    private String apiKey;

    @Value("${open-weather-map.data.fresh-data-duration-in-minutes}")
    private int freshDataDuration;

    @Transactional
    @Override
    public WeatherfulResponse getWeather(String city, String country) {
        return weatherDataRepository.findByCityIgnoreCaseAndCountryIgnoreCase(city, country)
            .filter(this::isDataFresh)
            .map(this::mapToWeatherResponse)
            .orElseGet(() -> mapToWeatherResponse(getWeatherFromExternalAndSaveToDb(city, country)));
    }

    private boolean isDataFresh(WeatherDataEntity entity) {
        return entity.getUpdatedDate().plusMinutes(freshDataDuration).isAfter(OffsetDateTime.now());
    }

    private WeatherfulResponse mapToWeatherResponse(WeatherDataEntity entity) {
        return WeatherfulResponse.builder()
            .city(entity.getCity())
            .country(entity.getCountry())
            .weatherDescription(entity.getWeatherDescription()).build();
    }

    private WeatherDataEntity getWeatherFromExternalAndSaveToDb(String city, String country) {
        try {
            String queryParam = city + ',' + country;
            JsonNode jsonNode = openWeatherApiClient.getWeatherByCityAndCountry(queryParam, apiKey);
            log.info("Successfully retrieved data from OpenWeatherData for city " + city + " country " + country);

            WeatherDataEntity entity = buildWeatherDataEntity(city, country, jsonNode);
            return weatherDataRepository.save(entity);
        } catch (WebClientResponseException.NotFound e) {
            throw new CityNotFoundException("Data not found for the city " + city + " country " + country);
        } catch (WebClientResponseException e) {
            throw new ServerSideException("Unexpected error occurred while calling external API", e);
        }
    }

    private WeatherDataEntity buildWeatherDataEntity(String city, String country, JsonNode jsonNode) {
        return WeatherDataEntity.builder()
            .id(jsonNode.get(ID_PATH).asLong())
            .city(city)
            .country(country)
            .weatherDescription(jsonNode.get(WEATHER_PATH).get(0).get(DESCRIPTION_PATH).asText())
            .updatedDate(OffsetDateTime.now())
            .build();
    }

}
