package com.github.jeffmadrid.weatherfulapp.model.entity.tokens;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@IdClass(TokenAccessId.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token_access")
public class TokenAccessEntity {
    @Id
    private String token;
    @Id
    private OffsetDateTime accessedDate;
}
