package com.learning.graphql_movie_application.dto.request;

import lombok.Data;

@Data
public class WatchListInput {
    private Integer customerId;
    private Integer movieId;
}
