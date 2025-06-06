package com.learning.graphql_crud_application.server.services;

import com.learning.graphql_crud_application.models.dto.Action;
import com.learning.graphql_crud_application.models.dto.CustomerDto;
import com.learning.graphql_crud_application.models.dto.CustomerEvent;
import com.learning.graphql_crud_application.models.dto.Status;
import com.learning.graphql_crud_application.models.response.DeleteResponseDto;
import com.learning.graphql_crud_application.server.repositories.CustomerRepository;
import com.learning.graphql_crud_application.server.utils.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEventService customerEventService;

    public Flux<CustomerDto> allCustomers() {
        return this.customerRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<CustomerDto> customerById(Integer id) {
        return this.customerRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<CustomerDto> createCustomer(CustomerDto dto) {
        return Mono.just(dto)
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.customerRepository::save)
                .map(EntityDtoUtil::toDto)
                .doOnNext(c -> this.customerEventService.emitEvent(CustomerEvent.create(c.getId(), Action.CREATED)));
    }

    public Mono<CustomerDto> updateCustomer(Integer id, CustomerDto dto) {
        return this.customerRepository.findById(id)
                .map(c -> EntityDtoUtil.toEntity(id, dto))
                .flatMap(this.customerRepository::save)
                .map(EntityDtoUtil::toDto)
                .doOnNext(c -> this.customerEventService.emitEvent(CustomerEvent.create(c.getId(), Action.UPDATED)));
    }

    public Mono<DeleteResponseDto> deleteCustomer(Integer id) {
        return this.customerRepository.deleteById(id)
                .doOnSuccess(c -> this.customerEventService.emitEvent(CustomerEvent.create(id, Action.DELETED)))
                .thenReturn(DeleteResponseDto.create(id, Status.SUCCESS))
                .onErrorReturn(DeleteResponseDto.create(id, Status.FAILURE));
    }
}
