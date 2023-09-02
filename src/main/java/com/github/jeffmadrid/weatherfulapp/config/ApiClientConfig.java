package com.github.jeffmadrid.weatherfulapp.config;

import com.github.jeffmadrid.weatherfulapp.service.weather.OpenWeatherApiClient;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Configuration
public class ApiClientConfig {

    @Value("${open-weather-map.url}")
    private String openWeatherMapUrl;

    @PostConstruct
    void postConstruct() {
        log.info("Open Weather Map target URL is {}", openWeatherMapUrl);
    }

    @Bean
    public OpenWeatherApiClient weatherApiClient(WebClient.Builder builder) {
        return HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(builder.baseUrl(openWeatherMapUrl).build()))
            .build()
            .createClient(OpenWeatherApiClient.class);
    }
}
