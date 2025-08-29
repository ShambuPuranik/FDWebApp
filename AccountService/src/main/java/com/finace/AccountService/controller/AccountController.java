package com.finace.AccountService.controller;


import com.finace.AccountService.dto.AccountDTO;
import com.finace.AccountService.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/debit/{accountId}/{amount}")
    public String debit(@PathVariable String accountId, @PathVariable double amount) {
        return accountService.debit(accountId, amount);
    }



    @PostMapping("/credit/{accountId}/{amount}")
    public String credit(@PathVariable String accountId, @PathVariable double amount) {
        return accountService.credit(accountId, amount);
    }

    @GetMapping("/balance/{accountId}")
    public double getBalance(@PathVariable String accountId) {
        return accountService.getBalance(accountId);
    }

    @PostMapping("/transfer/{amount}/{fromAccountId}/{toAccountId}")
    public String transferAmount( @PathVariable double amount,@PathVariable String fromAccountId, @PathVariable String toAccountId){
        return accountService.transfer(amount,fromAccountId,toAccountId);
    }

    @PostMapping("/createAccount")
    public AccountDTO creteAccount(@RequestBody AccountDTO accountDTO){
        return accountService.createAcount(accountDTO);
    }

    @PostMapping("/creditAmount/{accountId}/{amount}")
    public String creditAmount(@PathVariable String accountId, @PathVariable double amount){
        return accountService.creditAmount(accountId, amount);
    }

    @PostMapping("/debitAmount/{accountId}/{amount}")
    public String debitAmount(@PathVariable String accountId, @PathVariable double amount) {
        return accountService.debitAmount(accountId, amount);
    }





}
