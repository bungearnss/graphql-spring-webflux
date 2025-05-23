package com.learning.graphql_crud_application.server.services;

import com.learning.graphql_crud_application.models.dto.CustomerEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class CustomerEventService {

    private final Sinks.Many<CustomerEvent> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final Flux<CustomerEvent> flux = sink.asFlux().cache(0);

    public void emitEvent(CustomerEvent event) {
        this.sink.tryEmitNext(event);
    }

    public Flux<CustomerEvent> subscribe() {
        return this.flux;
    }

}