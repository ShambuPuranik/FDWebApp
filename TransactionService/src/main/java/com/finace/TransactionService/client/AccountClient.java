package com.finace.TransactionService.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "account-service") // Eureka will resolve this
public interface AccountClient {

    @PostMapping("/account/debit/{accountId}/{amount}")
    String debit(@PathVariable("accountId") String accountId, @PathVariable("amount") double amount );

    @PostMapping("/account/credit/{amount}")
    String credit(@PathVariable("amount") double amount);

    @PostMapping("/account/balance")
    double getBalance();

    @PostMapping("/account/debitAmount/{accountId}/{amount}")
    String debitAmount(@PathVariable String accountId, @PathVariable double amount);

    @PostMapping("/account/creditAmount/{accountId}/{amount}")
     String creditAmount(@PathVariable String accountId, @PathVariable double amount);



}
