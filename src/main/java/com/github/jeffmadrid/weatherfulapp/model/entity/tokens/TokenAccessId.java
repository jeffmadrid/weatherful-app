package com.github.jeffmadrid.weatherfulapp.model.entity.tokens;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenAccessId implements Serializable {
    private String token;
    private OffsetDateTime accessedDate;
}
