package com.finace.CustomerService.repository;

import com.finace.CustomerService.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
