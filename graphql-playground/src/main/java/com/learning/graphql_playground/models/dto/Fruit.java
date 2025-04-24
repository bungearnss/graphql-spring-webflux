package com.learning.graphql_playground.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Fruit {

    private final UUID id = UUID.randomUUID();
    private String description;
    private Integer price;
    private LocalDate expiryDate;
}
