# weatherful-app

This application acts as a façade to `OpenWeatherMap`to retrieve weather data based on a given `city` and `country`. [More details here.](https://openweathermap.org/current)

## Get Started

Start script to get started, test and to start the application.

```shell
./start.sh
```

The above the commands below: (An `API Key` for `OpenWeatherMap` is required)
```shell
./gradlew test
OPEN_WEATHER_MAP_API_KEY=<insert_own_api_key_here> ./gradlew bootRun
```

## Design & Implementation
- Java 17 Application: Spring Boot 3.1.3 (Spring 6) with H2 database backend.
- Spring Security with Filters for API Key validation before it reaches the `Controller` layer.
- Spring HTTP Interface are used to generate `intefaces` to communicate with external service. `WebClient` is used but in a blocking/non-reactive way.

## Libraries Used
- Spring Data JPA
- Spring Security
- Spring Webflux
- Spring Web
- Hibernate Validation
- Flyway
- H2 Database
- Lombok
- Mockito

## Assumptions
- The `id`'s received from downstream `OpenWeatherMap API` are unique for each combined city and country (case-insensitive).
- The persisted weather data are assumed to be valid/fresh only for 30 minutes. Configurable on property.
- `API Key` can also be called as `token`.
- There are only 5 API Keys to access and they currently live in the database backend.

## Future considerations

Below are feature that are not considered but could be implemented:

1. Idempotency
2. Retry implementation
