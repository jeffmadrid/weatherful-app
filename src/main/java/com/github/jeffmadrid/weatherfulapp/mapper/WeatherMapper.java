package com.github.jeffmadrid.weatherfulapp.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jeffmadrid.weatherfulapp.model.dto.WeatherResponse;
import com.github.jeffmadrid.weatherfulapp.model.entity.WeatherDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WeatherMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(target = "rawData", source = "source", qualifiedByName = "fromWeatherResponseToString")
    @Mapping(target = "city", source = "source.name")
    @Mapping(target = "country", source = "source.sys.country")
    public abstract WeatherDataEntity toEntity(WeatherResponse source);

    @Named("fromWeatherResponseToString")
    String fromWeatherResponseToString(WeatherResponse source) throws JsonProcessingException {
        if (Objects.nonNull(source)) {
            return objectMapper.writeValueAsString(source);
        }
        return null;
    }
}
