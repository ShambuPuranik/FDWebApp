package com.finace.CustomerService.mapper;

import com.finace.CustomerService.domain.Customer;
import com.finace.CustomerService.dto.CustomerDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDto(Customer entity);

    Customer toEntity(CustomerDTO dto);

    List<CustomerDTO> toDtoList(List<Customer> entities);

    List<Customer> toEntityList(List<CustomerDTO> dtoList);
}
