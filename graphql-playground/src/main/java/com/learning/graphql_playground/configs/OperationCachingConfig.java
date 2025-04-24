package com.learning.graphql_playground.configs;

import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.PreparsedDocumentProvider;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class OperationCachingConfig {

    @Bean
    public GraphQlSourceBuilderCustomizer sourceBuilderCustomizer(PreparsedDocumentProvider provider) {
        return c -> c.configureGraphQl(builder -> builder.preparsedDocumentProvider(provider));
    }

    @Bean
    public PreparsedDocumentProvider provider() {
        Map<String, PreparsedDocumentEntry> map = new ConcurrentHashMap<>();
        return (executionInput, parseAndValidateFunction) -> {
            var documentEntry = map.computeIfAbsent(executionInput.getQuery(), key -> {
                System.out.println("Not found : " + key);
                var r = parseAndValidateFunction.apply(executionInput);
                System.out.println("Caching : " + r);
                return r;
            });
            return CompletableFuture.completedFuture(documentEntry);
        };
    }
}
