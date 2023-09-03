package com.github.jeffmadrid.weatherfulapp.repository;

import com.github.jeffmadrid.weatherfulapp.model.entity.tokens.TokenAccessEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface TokenAccessRepository extends CrudRepository<TokenAccessEntity, String> {
    int countByTokenAndAccessedDateAfter(String token, OffsetDateTime dateTime);
}
