package com.learning.graphql_playground.configs;

import com.learning.graphql_playground.models.dto.Fruit;
import graphql.schema.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class TypeResolverConfig {

    @Bean
    public RuntimeWiringConfigurer typeResolverConfigurer(TypeResolver resolver){
        return c -> c.type("ProductDetail", b -> b.typeResolver(resolver));
    }

    @Bean
    public TypeResolver typeResolver(){
        ClassNameTypeResolver resolver = new ClassNameTypeResolver();
        resolver.addMapping(Fruit.class, "Fruit");
        return resolver;
    }
}
