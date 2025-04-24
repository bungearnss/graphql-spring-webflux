package com.learning.graphql_crud_application.controllers;

import com.learning.graphql_crud_application.models.dto.CustomerEvent;
import com.learning.graphql_crud_application.services.CustomerEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class CustomerEventController {

    @Autowired
    private CustomerEventService customerEventService;

    @SubscriptionMapping
    public Flux<CustomerEvent> customerEvents() {
        return this.customerEventService.subscribe();
    }

}
