package com.learning.graphql_playground.controllers;

import com.learning.graphql_playground.models.entity.Account;
import com.learning.graphql_playground.models.entity.Customer;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class AccountController {

    @SchemaMapping
    public Mono<Account> account(Customer customer) {
        var type = ThreadLocalRandom.current().nextBoolean() ? "CHECKING" : "SAVING";
        return Mono.just(
                Account.create(
                        UUID.randomUUID(),
                        ThreadLocalRandom.current().nextInt(1, 1000),
                        type
                )
        );
    }
}
