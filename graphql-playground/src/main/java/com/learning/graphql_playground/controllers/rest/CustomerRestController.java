package com.learning.graphql_playground.controllers.rest;

import com.learning.graphql_playground.models.entity.CustomerWithOrder;
import com.learning.graphql_playground.services.CustomerService;
import com.learning.graphql_playground.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/rest/customers")
    public Flux<CustomerWithOrder> customerOrders() {
        return this.customerService.allCustomers()
                .flatMap(c ->
                        this.orderService.ordersByCustomerName(c.getName())
                                .collectList()
                                .map(l -> CustomerWithOrder.create(c.getId(), c.getName(), c.getAge(), c.getCity(), l))
                );
    }

}
