package com.github.jeffmadrid.weatherfulapp.repository;

import com.github.jeffmadrid.weatherfulapp.model.entity.tokens.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, String> {
}
