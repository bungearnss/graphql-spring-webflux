package com.learning.graphql_movie_application.dto.request;

import com.learning.graphql_movie_application.dto.Genre;
import lombok.Data;

@Data
public class CustomerInput {
    private Integer id;
    private String name;
    private Genre favoriteGenre;
}