package com.github.jeffmadrid.weatherfulapp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@IdClass(WeatherDataId.class)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WeatherData")
public class WeatherDataEntity {
    @Id
    private String city;
    @Id
    private String country;
    private String rawData;
}
