package com.learning.graphql_movie_application.controllers;

import com.learning.graphql_movie_application.clients.CustomerClient;
import com.learning.graphql_movie_application.dto.Customer;
import com.learning.graphql_movie_application.dto.Status;
import com.learning.graphql_movie_application.dto.request.CustomerInput;
import com.learning.graphql_movie_application.dto.request.WatchListInput;
import com.learning.graphql_movie_application.dto.response.WatchListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Controller
public class CustomerController {

    @Autowired
    private CustomerClient customerClient;

    @QueryMapping
    public Mono<Customer> userProfile(@Argument Integer id) {
        return this.customerClient.getCustomer(id);
    }

    @MutationMapping
    public Mono<Customer> updateProfile(@Argument CustomerInput input) {
        return this.customerClient.updateCustomer(input);
    }

    @MutationMapping
    public Mono<WatchListResponse> addToWatchList(@Argument WatchListInput input) {
        return this.customerClient.addToWatchList(input)
                .map(list -> WatchListResponse.create(Status.SUCCESS, list))
                .doOnError(System.out::println)
                .onErrorReturn(WatchListResponse.create(Status.FAILURE, Collections.emptyList()));
    }
}
