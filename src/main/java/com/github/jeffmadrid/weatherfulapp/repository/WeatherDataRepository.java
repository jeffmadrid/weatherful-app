package com.github.jeffmadrid.weatherfulapp.repository;

import com.github.jeffmadrid.weatherfulapp.model.entity.WeatherDataId;
import com.github.jeffmadrid.weatherfulapp.model.entity.WeatherDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends CrudRepository<WeatherDataEntity, WeatherDataId> {
    Optional<WeatherDataEntity> findByCityAndCountryIgnoreCase(String city, String country);
}
