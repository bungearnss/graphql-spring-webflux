package com.learning.graphql_playground.controllers;

import com.learning.graphql_playground.models.dto.AllTypes;
import com.learning.graphql_playground.models.dto.Car;
import com.learning.graphql_playground.models.dto.Product;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Controller
public class ScalarController {

    private final AllTypes allTypes = AllTypes.create(
            UUID.randomUUID(),
            10,
            10.12f,
            "atlanta",
            false,
            120000000000L,
            Byte.valueOf("12"),
            Short.valueOf("100"),
            BigDecimal.valueOf(123456789.123456789),
            BigInteger.valueOf(1234567890),
            LocalDate.now(),
            LocalTime.now(),
            OffsetDateTime.now(),
            Car.HONDA
    );

    @QueryMapping
    public Mono<AllTypes> get() {
        return Mono.just(allTypes);
    }

    @QueryMapping
    public Flux<Product> products() {
        return Flux.just(
                Product.create("banana", Map.of(
                        "expiry date", "01/01/2025",
                        "color", "yellow"
                )),
                Product.create("mac", Map.of(
                        "cpu", "8",
                        "RAM", "32g"
                ))
        );
    }

}
