package com.learning.graphql_crud_application.server.helpers;

import org.springframework.context.annotation.Profile;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class RequestInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        var headers = request.getHeaders().getOrEmpty("caller-id");
        var callerId = headers.isEmpty() ? "" : headers.getFirst();
        request.configureExecutionInput((e, b) -> b.graphQLContext(Map.of("caller-id", callerId)).build());
        return chain.next(request);
    }

}
