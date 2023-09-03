package com.github.jeffmadrid.weatherfulapp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather_data")
public class WeatherDataEntity {
    @Id
    private Long id;
    private String city;
    private String country;
    private String weatherDescription;
    private OffsetDateTime updatedDate;
}
