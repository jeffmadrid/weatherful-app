package com.github.jeffmadrid.weatherfulapp.stubs;

import com.github.jeffmadrid.weatherfulapp.model.entity.tokens.TokenEntity;
import com.github.jeffmadrid.weatherfulapp.model.entity.tokens.TokenType;

import java.time.OffsetDateTime;

public class TokenEntityStubs {

    public static TokenEntity stubTokenEntity() {
        return TokenEntity.builder()
            .token("test-api-key")
            .type(TokenType.API_KEY)
            .createdDate(OffsetDateTime.now())
            .build();
    }
}
