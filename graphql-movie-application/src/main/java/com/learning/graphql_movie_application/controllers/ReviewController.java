package com.learning.graphql_movie_application.controllers;

import com.learning.graphql_movie_application.clients.ReviewClient;
import com.learning.graphql_movie_application.dto.Movie;
import com.learning.graphql_movie_application.dto.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class ReviewController {

    @Autowired
    private ReviewClient reviewClient;

    @SchemaMapping(typeName = "MovieDetails")
    public Flux<Review> reviews(Movie movie) {
        return this.reviewClient.reviews(movie.getId());
    }

}
