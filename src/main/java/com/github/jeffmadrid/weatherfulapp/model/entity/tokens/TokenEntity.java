package com.github.jeffmadrid.weatherfulapp.model.entity.tokens;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Token")
public class TokenEntity {
    @Id
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType type = TokenType.API_KEY;
    private OffsetDateTime createdDate;
}
