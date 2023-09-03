package com.github.jeffmadrid.weatherfulapp.stubs;

import com.github.jeffmadrid.weatherfulapp.model.entity.WeatherDataEntity;

import java.time.OffsetDateTime;

public class WeatherEntityStubs {
    public static WeatherDataEntity stubWeatherDataEntity() {
        return WeatherDataEntity.builder()
            .id(1L)
            .city("Tokyo")
            .country("JP")
            .weatherDescription("broken clouds")
            .updatedDate(OffsetDateTime.now())
            .build();
    }

    public static String stubWeatherJsonResponseAsString() {
        return """
            {
                "coord": {
                    "lon": 139.6917,
                    "lat": 35.6895
                },
                "weather": [
                    {
                        "id": 803,
                        "main": "Clouds",
                        "description": "broken clouds",
                        "icon": "04n"
                    }
                ],
                "base": "stations",
                "main": {
                    "temp": 301.74,
                    "feels_like": 306.58,
                    "temp_min": 299.68,
                    "temp_max": 302.73,
                    "pressure": 1013,
                    "humidity": 79
                },
                "visibility": 10000,
                "wind": {
                    "speed": 5.66,
                    "deg": 50
                },
                "clouds": {
                    "all": 75
                },
                "dt": 1693739382,
                "sys": {
                    "type": 2,
                    "id": 2044139,
                    "country": "JP",
                    "sunrise": 1693685660,
                    "sunset": 1693732071
                },
                "timezone": 32400,
                "id": 1850144,
                "name": "Tokyo",
                "cod": 200
            }
            """;
    }
}
