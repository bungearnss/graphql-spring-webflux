package com.learning.graphql_playground.controllers;

import com.learning.graphql_playground.models.dto.Book;
import com.learning.graphql_playground.models.dto.Electronics;
import com.learning.graphql_playground.models.dto.Fruit;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@Controller
public class SearchEngineController {

    List<Object> list = List.of(
            Fruit.create("orange", 1, LocalDate.now().plusDays(3)),
            Fruit.create("coconut", 2, LocalDate.now().plusDays(5)),
            Electronics.create("ipad", 600, "APPLE"),
            Electronics.create("tablet", 400, "SAMSUNG"),
            Book.create("node.js", 40, "venkat")
    );

    @QueryMapping
    public Flux<Object> search() {
        return Mono.fromSupplier(() -> new ArrayList<>(list))
                .doOnNext(Collections::shuffle)
                .flatMapIterable(Function.identity())
                .take(ThreadLocalRandom.current().nextInt(0, list.size()));
    }

}
