package com.learning.graphql_crud_application.configs;

import com.learning.graphql_crud_application.models.dto.CustomerDto;
import graphql.schema.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class TypeResolverConfig {

    @Bean
    public TypeResolver typeResolver() {
        ClassNameTypeResolver resolver = new ClassNameTypeResolver();
        resolver.addMapping(CustomerDto.class, "Customer");
        return resolver;
    }

    @Bean
    public RuntimeWiringConfigurer typeResolverConfigurer(TypeResolver resolver) {
        return c -> c.type("CustomerResponse", b -> b.typeResolver(resolver));
    }

}
