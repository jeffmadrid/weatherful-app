package com.github.jeffmadrid.weatherfulapp.model.entity.tokens;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class TokenEntity {
    @Id
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private OffsetDateTime createdDate;
}
