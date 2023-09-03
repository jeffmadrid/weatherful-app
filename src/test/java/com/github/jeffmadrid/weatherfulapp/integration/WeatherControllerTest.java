package com.github.jeffmadrid.weatherfulapp.integration;

import com.github.jeffmadrid.weatherfulapp.Constants;
import com.github.jeffmadrid.weatherfulapp.model.entity.tokens.TokenEntity;
import com.github.jeffmadrid.weatherfulapp.model.entity.tokens.TokenType;
import com.github.jeffmadrid.weatherfulapp.repository.TokenAccessRepository;
import com.github.jeffmadrid.weatherfulapp.repository.TokenRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherControllerTest extends AbstractIntegrationTest {
    private static final String API_KEY = "test-api-key";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TokenAccessRepository tokenAccessRepository;

    @BeforeAll
    void beforeAll() {
        tokenRepository.save(new TokenEntity(API_KEY, TokenType.API_KEY, OffsetDateTime.now()));
    }

    @AfterAll
    void afterAll() {
        tokenAccessRepository.deleteAll();
    }

    @Transactional
    @Test
    void testSuccessfullyGetDataFromOpenWeatherService() throws Exception {
        stubFor(get(urlPathEqualTo("/data/2.5/weather"))
            .willReturn(aResponse()
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile("openweathermap-weather-response-successful.json")
                .withStatus(HttpStatus.OK)));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather")
                .queryParam("city", "Zocca")
                .queryParam("country", "IT")
                .header(Constants.API_KEY_HEADER, API_KEY)
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("IT"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Zocca"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.weather.description").value("moderate rain"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        ",",
        "\"\",",
        ",\"\""
    })
    void testBadRequestGetDataFromOpenWeatherService(String city, String country) throws Exception {
        stubFor(get(urlPathEqualTo("/data/2.5/weather"))
            .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND)));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather")
                .queryParam("city", city)
                .queryParam("country", country)
                .header(Constants.API_KEY_HEADER, API_KEY)
            ).andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
    }

    @Test
    void testNotFoundGetDataFromOpenWeatherService() throws Exception {
        stubFor(get(urlPathEqualTo("/data/2.5/weather"))
            .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND)));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather")
                .queryParam("city", "Melbourne")
                .queryParam("country", "AU")
                .header(Constants.API_KEY_HEADER, API_KEY)
            ).andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Not Found"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
            .andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("Data not found for the city Melbourne country AU"));
    }

    @Test
    void testInternalServerErrorGetDataFromOpenWeatherService() throws Exception {
        stubFor(get(urlPathEqualTo("/data/2.5/weather"))
            .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR)));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/weather")
                .queryParam("city", "Melbourne")
                .queryParam("country", "AU")
                .header(Constants.API_KEY_HEADER, API_KEY)
            ).andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Internal Server Error"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500))
            .andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("Unexpected error occurred while calling external API"));
    }

}
