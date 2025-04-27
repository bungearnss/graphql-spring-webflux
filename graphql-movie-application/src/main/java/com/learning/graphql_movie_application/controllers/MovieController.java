package com.learning.graphql_movie_application.controllers;

import com.learning.graphql_movie_application.clients.MovieClient;
import com.learning.graphql_movie_application.dto.Customer;
import com.learning.graphql_movie_application.dto.Genre;
import com.learning.graphql_movie_application.dto.Movie;
import com.learning.graphql_movie_application.dto.response.WatchListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieClient movieClient;

    @SchemaMapping(typeName = "UserProfile")
    public Flux<Movie> watchList(Customer customer) {
        return this.movieClient.moviesByIds(customer.getWatchList());
    }

    @SchemaMapping(typeName = "UserProfile")
    public Flux<Movie> recommended(Customer customer) {
        return this.movieClient.moviesByGenre(customer.getFavoriteGenre());
    }

    @SchemaMapping(typeName = "WatchListResponse")
    public Flux<Movie> watchList(WatchListResponse watchListResponse) {
        return this.movieClient.moviesByIds(watchListResponse.getWatchList());
    }

    @QueryMapping
    public Mono<Movie> movieDetails(@Argument Integer id) {
        return this.movieClient.moviesByIds(List.of(id)).next();
    }

    @QueryMapping
    public Flux<Movie> moviesByGenre(@Argument Genre genre) {
        return this.movieClient.moviesByGenre(genre);
    }
}
