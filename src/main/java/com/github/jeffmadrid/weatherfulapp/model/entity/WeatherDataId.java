package com.github.jeffmadrid.weatherfulapp.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataId implements Serializable {
    private String city;
    private String country;
}
