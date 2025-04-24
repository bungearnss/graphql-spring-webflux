package com.learning.graphql_playground.controllers;

import com.learning.graphql_playground.models.dto.Book;
import com.learning.graphql_playground.models.dto.Electronics;
import com.learning.graphql_playground.models.dto.Fruit;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Controller
public class ProductDetailController {

    @QueryMapping
    public Flux<Object> productDetail() {
        return Flux.just(
                Fruit.create("banana", 1, LocalDate.now().plusDays(3)),
                Fruit.create("apple", 2, LocalDate.now().plusDays(5)),
                Electronics.create("mac book", 600, "APPLE"),
                Electronics.create("phone", 400, "SAMSUNG"),
                Book.create("java", 40, "venkat")
        );
    }
}
