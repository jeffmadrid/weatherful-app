package com.github.jeffmadrid.weatherfulapp.integration;

import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;
import com.github.jeffmadrid.weatherfulapp.service.weather.OpenWeatherApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import stubs.OpenWeatherMapStub;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OpenWeatherApiClientTest extends AbstractIntegrationTest {

    @Autowired
    OpenWeatherApiClient openWeatherApiClient;

    @Test
    void testSuccessfullyGetGeoDataByCity() {
        stubFor(get(urlPathEqualTo("/data/2.5/weather"))
            .willReturn(aResponse()
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile("openweathermap-weather-response-successful.json")
                .withStatus(HttpStatus.OK))
        );

        WeatherResponse response = openWeatherApiClient.getWeatherByCityAndCountry("Zocca,IT", "test");

        assertThat(response).isEqualTo(OpenWeatherMapStub.stubWeatherResponse());
    }

    @ParameterizedTest
    @ValueSource(ints = {HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_REQUEST, HttpStatus.UNAUTHORIZED})
    void testFailedGetGeoDataByCity(int statusCode) {
        stubFor(get(urlPathEqualTo("/data/2.5/weather"))
            .willReturn(aResponse().withStatus(statusCode))
        );

        assertThrows(WebClientResponseException.class,
            () -> openWeatherApiClient.getWeatherByCityAndCountry("London,UK", "test"));
    }

}
