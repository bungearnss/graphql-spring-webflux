package com.learning.graphql_crud_application.client.node;

import com.learning.graphql_crud_application.models.dto.CustomerEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Flux;

@Service
public class SubscriptionClient {

    private final WebSocketGraphQlClient client;

    public SubscriptionClient(@Value("${customer.events.subscription.url}") String baseUrl) {
        this.client = WebSocketGraphQlClient.builder(baseUrl, new ReactorNettyWebSocketClient()).build();
    }

    public Flux<CustomerEvent> customerEvents() {
        var doc = """
                        subscription{
                            customerEvents{
                                id
                                action
                            }
                        }\
                """;
        return this.client
                .mutate().header("caller-id", "123ABC").build()
                .document(doc)
                .retrieveSubscription("customerEvents")
                .toEntity(CustomerEvent.class);
    }

}
