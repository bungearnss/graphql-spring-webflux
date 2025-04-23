package com.learning.graphql_playground.services;

import com.learning.graphql_playground.models.entity.Customer;
import com.learning.graphql_playground.models.dto.CustomerOrder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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

    public Flux<List<CustomerOrder>> ordersByCustomerNames(List<String> names) {
        return Flux.fromIterable(names)
                .flatMapSequential(n -> fetchOrders(n).defaultIfEmpty(Collections.emptyList()));
    }

    private Mono<List<CustomerOrder>> fetchOrders(String name) {
        return Mono.justOrEmpty(map.get(name))
                .delayElement(Duration.ofMillis(ThreadLocalRandom.current().nextInt(0, 500)));
    }

    public Mono<Map<Customer, List<CustomerOrder>>> fetchOrdersAsMap(List<Customer> customers) {
        return Flux.fromIterable(customers)
                .map(c -> Tuples.of(c, map.getOrDefault(c.getName(), Collections.emptyList())))
                .collectMap(
                        Tuple2::getT1,
                        Tuple2::getT2
                );
    }
}
