package com.learning.graphql_playground.configs;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class ScalarConfig {

    @Bean
    public RuntimeWiringConfigurer scalarConfigurer() {
        return c -> c
                .scalar(ExtendedScalars.GraphQLLong)
                .scalar(ExtendedScalars.GraphQLByte)
                .scalar(ExtendedScalars.GraphQLShort)
                .scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.GraphQLBigInteger)
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.LocalTime)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.Object);
    }
}
