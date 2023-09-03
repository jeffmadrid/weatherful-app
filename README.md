# weatherful-app

This application acts as a façade to `OpenWeatherMap`to retrieve weather data based on a given `city` and `country`. [More details of the external API here.](https://openweathermap.org/current)

## Get Started

Start script to get started, test and run the application.

```shell
./start.sh
```

The above the commands below: (An `API Key` for `OpenWeatherMap` is required)
```shell
./gradlew test
OPEN_WEATHER_MAP_API_KEY=<insert_own_api_key_here> ./gradlew bootRun
```

The following `curl` can be used to call the endpoint of this service.
```shell
curl --request GET \
  --url 'http://localhost:8080/v1/weather?city=Melbourne&country=AU' \
  --header 'X-Api-Key: AK001'
```

## Design & Implementation
- Java 17 Application: Spring Boot 3.1.3 (Spring 6) with H2 database backend.
- Spring Security with Filters for API Key validation before it reaches the `Controller` layer.
- Spring HTTP Interface are used to generate `interfaces` to communicate with external service. `WebClient` is used but in a blocking/non-reactive way.
- The API Key to call this service is stored in the HTTP header with the name `X-Api-Key`.
- Queries for `city` and `country` are case-insensitive.
- Flyway script is used for database migration of Relational Tables and insertion of API Keys.

## Library Usage
- Spring Data JPA
- Spring Security
- Spring Webflux
- Spring Web
- Hibernate Validation
- Flyway
- H2 Database
- Lombok
- WireMock (from spring-cloud-starter-contract-stub-runner)
- Mockito (from spring-cloud-starter-contract-stub-runner)

Please refer to [build.gradle.kts](build.gradle.kts) for specific version. They should be compatible with Spring Boot version used as most (if not all) are not pinned with specific version and relies on Gradle plugin to ensure the compatibility.

```shell
# To view the dependency tree
./gradlew dependencies
```

## Assumptions
- This service assumes the `id`'s received from downstream `OpenWeatherMap API` are unique for each combined city and country (case-insensitive).
- The persisted weather data are assumed to be valid/fresh only for 30 minutes. Configurable on Java property.
- `API Key` is sometimes also referred to as `token`.
- There are only 5 API Keys to access and they currently live in the database backend.
- Successful and unsuccessful calls using API Key are considered in the rate limit count of 5 per hour.

## Future considerations

Below are feature that are not considered but could be implemented:

1. Idempotency
2. Retry implementation
