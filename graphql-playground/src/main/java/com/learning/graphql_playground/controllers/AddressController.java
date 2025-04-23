package com.learning.graphql_playground.controllers;

import com.learning.graphql_playground.models.entity.Address;
import com.learning.graphql_playground.models.entity.Customer;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class AddressController {

    @SchemaMapping
    public Mono<Address> address(Customer customer) {
        return Mono.just(
                Address.create(customer.getName() + " street", customer.getName() + " city")
        );
    }
}
