package com.github.jeffmadrid.weatherfulapp.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;
import com.github.jeffmadrid.weatherfulapp.model.entity.WeatherDataEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import stubs.OpenWeatherMapStub;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherMapperTest {

    WeatherMapper weatherMapper = Mappers.getMapper(WeatherMapper.class);

    @Test
    void testToEntity() throws JsonProcessingException {
        WeatherResponse weatherResponse = OpenWeatherMapStub.stubWeatherResponse();

        WeatherDataEntity entity = weatherMapper.toEntity(weatherResponse);

        assertThat(entity.getCity()).isEqualTo(weatherResponse.name());
        assertThat(entity.getCountry()).isEqualTo(weatherResponse.sys().country());
        assertThat(entity.getRawData()).isEqualTo(new ObjectMapper().writeValueAsString(weatherResponse));
    }
}
