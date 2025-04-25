package com.learning.graphql_crud_application;

import com.learning.graphql_crud_application.models.dto.Action;
import com.learning.graphql_crud_application.models.dto.CustomerEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureHttpGraphQlTester
@TestPropertySource(locations = "classpath:application-test.properties")
public class GraphqlSubscriptionTest {

    private static final String WS_PATH = "ws://localhost:8080/graphql";

    @Autowired
    private HttpGraphQlTester client;

    @Test
    public void subscriptionTest() {
        var websocketClient = WebSocketGraphQlTester
                .builder(WS_PATH, new ReactorNettyWebSocketClient())
                .build();

        // delete a customer
        this.client.mutate().header("caller-id", "demo").build()
                .documentName("crud-operations")
                .operationName("DeleteCustomer")
                .variable("id", 1)
                .executeAndVerify();

        websocketClient.mutate().header("caller-id", "demo").build()
                .documentName("subscription-test")
                .executeSubscription()
                .toFlux("customerEvents", CustomerEvent.class)
                .take(1)
                .as(StepVerifier::create)
                .consumeNextWith(e -> Assertions.assertThat(e.getAction()).isEqualTo(Action.CREATED))
                .verifyComplete();
    }
}