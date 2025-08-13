package com.finace.CustomerService.service.impl;

import com.finace.CustomerService.domain.Customer;
import com.finace.CustomerService.dto.CustomerDTO;
//import com.finace.CustomerService.mapper.CustomerMapper;
import com.finace.CustomerService.mapper.CustomerMapper;
import com.finace.CustomerService.repository.CustomerRepo;
import com.finace.CustomerService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepo.save(customer);
        CustomerDTO customerDTO1 = customerMapper.toDto(savedCustomer);
        return customerDTO1;
    }

    @Override
    public CustomerDTO geCustomerById(Long id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if( customer.isPresent() ){
            CustomerDTO customerDTO = customerMapper.toDto(customer.get());
            return customerDTO;
        }
        return null;

    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer : customers){
            CustomerDTO customerDTO = customerMapper.toDto(customer);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);

    }
}
