package com.learning.graphql_crud_application.server.utils;

import com.learning.graphql_crud_application.models.dto.CustomerDto;
import com.learning.graphql_crud_application.models.entity.Customer;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static Customer toEntity(CustomerDto dto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        return customer;
    }

    public static Customer toEntity(Integer id, CustomerDto dto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        customer.setId(id);
        return customer;
    }

    public static CustomerDto toDto(Customer customer){
        CustomerDto dto = new CustomerDto();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }

}
