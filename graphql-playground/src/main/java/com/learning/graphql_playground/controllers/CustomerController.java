package com.learning.graphql_playground.controllers;

import com.learning.graphql_playground.models.entity.AgeRangeFilter;
import com.learning.graphql_playground.models.entity.Customer;
import com.learning.graphql_playground.models.dto.CustomerOrder;
import com.learning.graphql_playground.services.CustomerService;
import com.learning.graphql_playground.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @SchemaMapping(typeName = "Query")
    public Flux<Customer> customers() {
        return this.customerService.allCustomers();
    }

    @QueryMapping
    public Mono<Customer> customerById(@Argument Integer id) {
        return this.customerService.customerById(id);
    }

    @QueryMapping
    public Flux<Customer> customersNameContains(@Argument String name) {
        return this.customerService.nameContains(name);
    }

    @QueryMapping
    public Flux<Customer> customersByAgeRange(@Argument AgeRangeFilter filter) {
        return this.customerService.withinAge(filter);
    }

    @SchemaMapping(typeName = "Customer")
    public Flux<CustomerOrder> ordersLimit(Customer customer, @Argument Integer limit) {
        System.out.println("Orders method invoked for " + customer.getName());
        return this.orderService.ordersByCustomerName(customer.getName())
                .take(limit);
    }

    // N + 1
//    @BatchMapping(typeName = "Customer")
//    public Flux<List<CustomerOrder>> orders(List<Customer> list){
//        System.out.println("Orders method invoked for " + list);
//        return this.orderService.ordersByCustomerNames(
//            list.stream().map(Customer::getName).collect(Collectors.toList())
//        );
//    }

    @BatchMapping(typeName = "Customer")
    public Mono<Map<Customer, List<CustomerOrder>>> orders(List<Customer> list) {
        System.out.println("Orders method invoked for " + list);
        return this.orderService.fetchOrdersAsMap(list);
    }
}
