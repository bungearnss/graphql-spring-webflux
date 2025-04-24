package com.learning.graphql_crud_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class GraphqlCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlCrudApplication.class, args);
	}

}
