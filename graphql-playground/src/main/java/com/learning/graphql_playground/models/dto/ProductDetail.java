package com.learning.graphql_playground.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductDetail {

    private String id;
    private String description;
    private Integer price;
}
