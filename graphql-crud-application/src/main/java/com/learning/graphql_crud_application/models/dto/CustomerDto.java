package com.learning.graphql_crud_application.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learning.graphql_crud_application.models.response.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto implements CustomerResponse {

    private Integer id;
    private String name;
    private Integer age;
    private String city;

}