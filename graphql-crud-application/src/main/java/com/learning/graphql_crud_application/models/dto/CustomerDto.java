package com.learning.graphql_crud_application.models.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private Integer id;
    private String name;
    private Integer age;
    private String city;

}