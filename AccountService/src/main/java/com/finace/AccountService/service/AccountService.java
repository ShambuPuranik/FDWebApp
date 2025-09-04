package com.finace.AccountService.service;



import com.finace.AccountService.AccountRepo;
import com.finace.AccountService.domain.Account;
import com.finace.AccountService.dto.AccountDTO;
import com.finace.AccountService.exception.AccountNotFoundException;
import com.finace.AccountService.exception.InsufficientBalanceException;
import com.finace.AccountService.mapper.AccountMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private  AccountRepo accountRepo;

    @Autowired
    private AccountMapper accountMapper;

    private Account getAccountOrThrow(String accountId) {
        Account account = accountRepo.findByAccountId(accountId);
        if (account == null) {
            throw new InsufficientBalanceException("Account with ID " + accountId + " not found");
        }
        return account;
    }


    @Transactional
    public String transfer(double amount, String fromAccountId, String toAccountId) {
    debitAmount(fromAccountId, amount);
    creditAmount(toAccountId, amount);
    return "Transferred " + amount + " from " + fromAccountId + " to " + toAccountId;
}

    public double getBalance(String accountId) {
        Account account = accountRepo.findByAccountId(accountId);
        return  account.getBalance();
    }


    public AccountDTO createAcount(AccountDTO accountDTO){
        Account account = accountRepo.save(accountMapper.toEntity(accountDTO));
        return accountMapper.toDto(account);
    }

    public String creditAmount(String  accountId, double amount){

//      Account account =  accountRepo.findByAccountId(accountId);
        Account account = getAccountOrThrow(accountId);
        account.setBalance(account.getBalance()+amount);
        accountRepo.save(account);
        return "Success";
    }

    public String debitAmount(String  accountId, double amount){
//       Account account = accountRepo.findByAccountId(accountId);
        Account account =   getAccountOrThrow(accountId);
        if (amount > account.getBalance()) {
            throw new RuntimeException("Insufficient balance in " + accountId);
        }
               double finalAmt = account.getBalance()-amount;
               account.setBalance(finalAmt);
               accountRepo.save(account);
                return "Balance After debit :" +finalAmt;



    }
}
