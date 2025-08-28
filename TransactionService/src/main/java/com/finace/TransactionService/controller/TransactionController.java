package com.finace.TransactionService.controller;



import com.finace.TransactionService.service.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/debit/{accountId}/{amount}")
    public String debit(@PathVariable String accountId, @PathVariable double amount) {
        return transactionService.debitAccount(accountId, amount);
    }
}
