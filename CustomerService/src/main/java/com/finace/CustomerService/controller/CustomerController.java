package com.finace.CustomerService.controller;


import com.finace.CustomerService.dto.CustomerDTO;
import com.finace.CustomerService.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerDTO create(@RequestBody CustomerDTO dto) {
        return customerService.createCustomer(dto);
    }

    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Long id){
        return customerService.geCustomerById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        customerService.deleteCustomer(id);

    }

    @GetMapping("/all")
    public List<CustomerDTO> getAllCustomer(){
       List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
        return  customerDTOs;
    }


}
