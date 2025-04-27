package com.learning.graphql_movie_application.clients;

import com.learning.graphql_movie_application.dto.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ReviewClient {

    private final WebClient webClient;

    public ReviewClient(@Value("${review.service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<Review> reviews(Integer movieId) {
        return this.webClient.get()
                .uri("{id}", movieId)
                .retrieve()
                .bodyToFlux(Review.class);
    }
}
