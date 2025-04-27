package com.learning.graphql_movie_application.clients;

import com.learning.graphql_movie_application.dto.Customer;
import com.learning.graphql_movie_application.dto.request.CustomerInput;
import com.learning.graphql_movie_application.dto.request.WatchListInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerClient {

    private final WebClient webClient;

    public CustomerClient(@Value("${customer.service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }


    public Mono<Customer> getCustomer(Integer id) {
        return this.webClient.get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    public Mono<Customer> updateCustomer(CustomerInput customerInput) {
        return this.webClient.put()
                .uri("{id}", customerInput.getId())
                .bodyValue(customerInput)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    public Mono<List<Integer>> addToWatchList(WatchListInput watchListInput) {
        return this.webClient.post()
                .uri("watchlist")
                .bodyValue(watchListInput)
                .retrieve()
                .bodyToFlux(Integer.class)
                .collectList();
    }
}
