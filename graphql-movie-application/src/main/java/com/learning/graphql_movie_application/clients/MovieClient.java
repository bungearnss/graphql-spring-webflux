package com.learning.graphql_movie_application.clients;

import com.learning.graphql_movie_application.dto.Genre;
import com.learning.graphql_movie_application.dto.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MovieClient {

    private final WebClient webClient;

    public MovieClient(@Value("${movie.service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<Movie> moviesByIds(List<Integer> ids) {
        return ids.isEmpty() ? Flux.empty() : getMoviesByIds(ids);
    }

    private Flux<Movie> getMoviesByIds(List<Integer> ids) {
        return this.webClient.get()
                .uri(b -> b.queryParam("ids", ids).build())
                .retrieve()
                .bodyToFlux(Movie.class);
    }

    public Flux<Movie> moviesByGenre(Genre genre) {
        return this.webClient.get()
                .uri("{genre}/recommended", genre)
                .retrieve()
                .bodyToFlux(Movie.class);
    }
}
