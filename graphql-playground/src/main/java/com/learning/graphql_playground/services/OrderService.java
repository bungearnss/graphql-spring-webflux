package com.learning.graphql_playground.services;

import com.learning.graphql_playground.models.dto.CustomerOrder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {

    private final Map<String, List<CustomerOrder>> map = Map.of(
            "sam", List.of(
                    CustomerOrder.create(UUID.randomUUID(), "sam-product-1"),
                    CustomerOrder.create(UUID.randomUUID(), "sam-product-2")
            ),
            "mike", List.of(
                    CustomerOrder.create(UUID.randomUUID(), "mike-product-1"),
                    CustomerOrder.create(UUID.randomUUID(), "mike-product-2"),
                    CustomerOrder.create(UUID.randomUUID(), "mike-product-3")
            )
    );

    public Flux<CustomerOrder> ordersByCustomerName(String name) {
        return Flux.fromIterable(map.getOrDefault(name, Collections.emptyList()));
    }
}
