package com.github.jeffmadrid.weatherfulapp.service.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jeffmadrid.weatherfulapp.exception.CityNotFoundException;
import com.github.jeffmadrid.weatherfulapp.exception.ServerSideException;
import com.github.jeffmadrid.weatherfulapp.model.api.WeatherfulResponse;
import com.github.jeffmadrid.weatherfulapp.model.entity.WeatherDataEntity;
import com.github.jeffmadrid.weatherfulapp.repository.WeatherDataRepository;
import com.github.jeffmadrid.weatherfulapp.stubs.WeatherEntityStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherServiceTest {

    private static final String CITY = "Tokyo";
    private static final String COUNTRY = "JP";

    @InjectMocks
    private OpenWeatherService openWeatherService;

    @Mock
    private OpenWeatherApiClient openWeatherApiClient;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(openWeatherService, "freshDataDuration", 5);
        ReflectionTestUtils.setField(openWeatherService, "apiKey", "test-api-key");
    }

    @Test
    void testRetrieveWeatherDataFromDatabase() {
        WeatherDataEntity entity = WeatherEntityStubs.stubWeatherDataEntity();
        when(weatherDataRepository.findByCityIgnoreCaseAndCountryIgnoreCase(any(), any()))
            .thenReturn(Optional.of(entity));

        WeatherfulResponse weather = openWeatherService.getWeather(CITY, COUNTRY);
        assertThat(weather.city()).isEqualTo(CITY);
        assertThat(weather.country()).isEqualTo(COUNTRY);
        assertThat(weather.weatherDescription()).isEqualTo(entity.getWeatherDescription());

        verify(openWeatherApiClient, never()).getWeatherByCityAndCountry(any(), any());
        verify(weatherDataRepository, never()).save(any());
    }

    @Test
    void testRetrieveWeatherFromOpenWeatherMapApiAndSaveToDatabase() throws IOException {
        WeatherDataEntity entity = WeatherEntityStubs.stubWeatherDataEntity();
        entity.setUpdatedDate(entity.getUpdatedDate().minusHours(1));
        when(weatherDataRepository.findByCityIgnoreCaseAndCountryIgnoreCase(any(), any()))
            .thenReturn(Optional.of(entity));
        when(openWeatherApiClient.getWeatherByCityAndCountry(any(), any()))
            .thenReturn(new ObjectMapper().readTree(WeatherEntityStubs.stubWeatherJsonResponseAsString()));
        when(weatherDataRepository.save(any())).thenReturn(entity);

        WeatherfulResponse weather = openWeatherService.getWeather(CITY, COUNTRY);
        assertThat(weather.city()).isEqualTo(CITY);
        assertThat(weather.country()).isEqualTo(COUNTRY);
        assertThat(weather.weatherDescription()).isEqualTo(entity.getWeatherDescription());

        verify(weatherDataRepository, atMostOnce()).findByCityIgnoreCaseAndCountryIgnoreCase(any(), any());
        verify(openWeatherApiClient, atMostOnce()).getWeatherByCityAndCountry(any(), any());
        verify(weatherDataRepository, atMostOnce()).save(any());
    }

    @Test
    void testRetrieveWeatherFromOpenWeatherMapApiWhenNoEntityOnDatabaseAndSaveToDatabase() throws IOException {
        WeatherDataEntity entity = WeatherEntityStubs.stubWeatherDataEntity();
        when(weatherDataRepository.findByCityIgnoreCaseAndCountryIgnoreCase(any(), any()))
            .thenReturn(Optional.empty());
        when(openWeatherApiClient.getWeatherByCityAndCountry(any(), any()))
            .thenReturn(new ObjectMapper().readTree(WeatherEntityStubs.stubWeatherJsonResponseAsString()));
        when(weatherDataRepository.save(any())).thenReturn(entity);

        WeatherfulResponse weather = openWeatherService.getWeather(CITY, COUNTRY);
        assertThat(weather.city()).isEqualTo(CITY);
        assertThat(weather.country()).isEqualTo(COUNTRY);
        assertThat(weather.weatherDescription()).isEqualTo(entity.getWeatherDescription());

        verify(weatherDataRepository, atMostOnce()).findByCityIgnoreCaseAndCountryIgnoreCase(any(), any());
        verify(openWeatherApiClient, atMostOnce()).getWeatherByCityAndCountry(any(), any());
        verify(weatherDataRepository, atMostOnce()).save(any());
    }

    @Test
    void testRetrieveWeatherFromOpenWeatherMapApiAndRaisesNotFoundException() {
        when(weatherDataRepository.findByCityIgnoreCaseAndCountryIgnoreCase(any(), any()))
            .thenReturn(Optional.empty());
        when(openWeatherApiClient.getWeatherByCityAndCountry(any(), any()))
            .thenThrow(WebClientResponseException.NotFound.class);

        assertThatThrownBy(() -> openWeatherService.getWeather(CITY, COUNTRY))
            .isInstanceOf(CityNotFoundException.class)
            .message()
            .contains("Data not found for the city");

        verify(weatherDataRepository, atMostOnce()).findByCityIgnoreCaseAndCountryIgnoreCase(any(), any());
        verify(openWeatherApiClient, atMostOnce()).getWeatherByCityAndCountry(any(), any());
        verify(weatherDataRepository, never()).save(any());
    }

    @Test
    void testRetrieveWeatherFromOpenWeatherMapApiAndRaisesServerSideException() {
        when(weatherDataRepository.findByCityIgnoreCaseAndCountryIgnoreCase(any(), any()))
            .thenReturn(Optional.empty());
        when(openWeatherApiClient.getWeatherByCityAndCountry(any(), any()))
            .thenThrow(WebClientResponseException.InternalServerError.class);

        assertThatThrownBy(() -> openWeatherService.getWeather(CITY, COUNTRY))
            .isInstanceOf(ServerSideException.class)
            .message()
            .isEqualTo("Unexpected error occurred while calling external API");

        verify(weatherDataRepository, atMostOnce()).findByCityIgnoreCaseAndCountryIgnoreCase(any(), any());
        verify(openWeatherApiClient, atMostOnce()).getWeatherByCityAndCountry(any(), any());
        verify(weatherDataRepository, never()).save(any());
    }
}
