package com.learning.graphql_crud_application.server.controllers;

import com.learning.graphql_crud_application.server.exceptions.ApplicationErrors;
import com.learning.graphql_crud_application.models.dto.CustomerDto;
import com.learning.graphql_crud_application.models.response.CustomerNotFound;
import com.learning.graphql_crud_application.models.response.DeleteResponseDto;
import com.learning.graphql_crud_application.server.services.CustomerService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @QueryMapping
    public Flux<CustomerDto> customers(DataFetchingEnvironment environment) {
        var callerId = environment.getGraphQlContext().get("caller-id");
        System.out.println("CALLER ID : " + callerId);
        return this.customerService.allCustomers();
    }

    @QueryMapping
    public Mono<Object> customerById(@Argument Integer id) {
        return this.customerService.customerById(id)
                .cast(Object.class)
                .switchIfEmpty(Mono.just(CustomerNotFound.create(id)));
    }

    @MutationMapping
    public Mono<CustomerDto> createCustomer(@Argument CustomerDto customer) {
        return Mono.just(customer)
                .filter(c -> c.getAge() >= 18)
                .flatMap(this.customerService::createCustomer)
                .switchIfEmpty(ApplicationErrors.mustBe18(customer));
    }

    @MutationMapping
    public Mono<CustomerDto> updateCustomer(@Argument Integer id, @Argument("customer") CustomerDto dto) {
        return this.customerService.updateCustomer(id, dto);
    }

    @MutationMapping
    public Mono<DeleteResponseDto> deleteCustomer(@Argument Integer id) {
        return this.customerService.deleteCustomer(id);
    }

}
