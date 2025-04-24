package com.learning.graphql_crud_application.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CustomerNotFound {

    private Integer id;
    private final String message = "user not found";

}
