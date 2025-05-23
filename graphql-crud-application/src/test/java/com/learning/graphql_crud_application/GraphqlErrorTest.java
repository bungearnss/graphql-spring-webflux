package com.learning.graphql_crud_application;

import com.learning.graphql_crud_application.models.dto.CustomerDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestPropertySource(locations = "classpath:application-test.properties")
public class GraphqlErrorTest {

    @Autowired
    private HttpGraphQlTester client;

    @Test
    public void createCustomerTest(){
        this.client
                .mutate().header("caller-id", "demo").build()
                .documentName("crud-operations")
                .variable("customer", CustomerDto.create(null, "michael", 15, "seattle"))
                .operationName("CreateCustomer")
                .execute()
                .errors().satisfy(list -> {
                    Assertions.assertThat(list).hasSize(1);
                    Assertions.assertThat(list.getFirst().getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
                })
                .path("response").valueIsNull();

        /*
            {
                "errors": [...],
                "response": null
            }
         */
    }
}