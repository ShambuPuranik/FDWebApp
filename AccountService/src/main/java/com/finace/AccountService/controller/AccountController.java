package com.finace.AccountService.controller;


import com.finace.AccountService.AccountRepo;
import com.finace.AccountService.dto.AccountDTO;
import com.finace.AccountService.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    private AccountRepo accountRepo;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/debit/{accountId}/{amount}")
    public ResponseEntity<String> debit(@PathVariable String accountId, @PathVariable double amount) {
        String response = accountService.debit(accountId, amount);
        return ResponseEntity.ok(response);

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
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO){
        if(accountRepo.existsByAccountId(accountDTO.getAccountId())){
            throw  new RuntimeException("AccountId already exist :" +accountDTO.getAccountId());
        }
        AccountDTO accountDTO1 = accountService.createAcount(accountDTO);
        return ResponseEntity.ok(accountDTO1);
    }

    @PostMapping("/creditAmount/{accountId}/{amount}")
    public ResponseEntity<String> creditAmount(@PathVariable String accountId, @PathVariable double amount){
       String response = accountService.creditAmount(accountId, amount);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debitAmount/{accountId}/{amount}")
    public ResponseEntity<String> debitAmount(@PathVariable String accountId, @PathVariable double amount) {
        String response = accountService.debitAmount(accountId, amount);
        return ResponseEntity.ok(response);
    }





}
