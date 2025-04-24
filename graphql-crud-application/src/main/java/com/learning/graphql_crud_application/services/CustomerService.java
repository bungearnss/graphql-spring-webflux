package com.learning.graphql_crud_application.services;

import com.learning.graphql_crud_application.models.dto.CustomerDto;
import com.learning.graphql_crud_application.models.dto.Status;
import com.learning.graphql_crud_application.models.response.DeleteResponseDto;
import com.learning.graphql_crud_application.repositories.CustomerRepository;
import com.learning.graphql_crud_application.utils.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

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
                .map(EntityDtoUtil::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Integer id, CustomerDto dto) {
        return this.customerRepository.findById(id)
                .map(c -> EntityDtoUtil.toEntity(id, dto))
                .flatMap(this.customerRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<DeleteResponseDto> deleteCustomer(Integer id) {
        return this.customerRepository.deleteById(id)
                .thenReturn(DeleteResponseDto.create(id, Status.SUCCESS))
                .onErrorReturn(DeleteResponseDto.create(id, Status.FAILURE));
    }
}
