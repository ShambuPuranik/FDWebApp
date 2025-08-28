package com.finace.AccountService.service;



import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

//    private double balance = 5000.0; // sample balance
//
//    public String debit(double amount) {
//        if (amount > balance) {
//            throw new RuntimeException("Insufficient balance. Available: " + balance);
//        }
//        balance -= amount;
//        return "Debited " + amount + ". Remaining balance: " + balance;
//    }
//    public String credit(double amount) {
//        balance += amount;
//        return " Credited " + amount + ". New balance: " + balance;
//    }
//
//    public double getBalance() {
//        return balance;
//    }

    private final Map<String, Double> accounts = new HashMap<>();

    public AccountService() {
        // initialize with some balances
        accounts.put("A", 5000.0);
        accounts.put("B", 2000.0);
    }

    public String debit(String accountId, double amount) {
        if (!accounts.containsKey(accountId)) {
            throw new RuntimeException("Account not found: " + accountId);
        }

        double balance = accounts.get(accountId);
        if (amount > balance) {
            throw new RuntimeException("Insufficient balance in " + accountId + ". Available: " + balance);
        }
        accounts.put(accountId, balance - amount);
        return "Debited " + amount + " from " + accountId + ". Remaining: " + accounts.get(accountId);
    }

    public String credit(String accountId, double amount) {
        if (!accounts.containsKey(accountId)) {
            throw new RuntimeException("Account not found: " + accountId);
        }
        accounts.put(accountId, accounts.get(accountId) + amount);
        return "Credited " + amount + " to " + accountId + ". New balance: " + accounts.get(accountId);
    }

    public String transfer(@PathVariable double amount, @PathVariable String fromAccountId, @PathVariable String toAccountId ){
        if (!accounts.containsKey(fromAccountId)) {
            throw new RuntimeException("Account not found: " + fromAccountId);
        }

        double balance = accounts.get(fromAccountId);
        if (amount > balance) {
            throw new RuntimeException("Insufficient balance in " + fromAccountId + ". Available: " + balance);
        }
        accounts.put(fromAccountId, balance - amount);
       System.out.println("Debited " + amount + " from " + fromAccountId + ". Remaining: " + accounts.get(fromAccountId));
        accounts.put(toAccountId, accounts.get(toAccountId) + amount);
        System.out.println( "Credited " + amount + " to " + toAccountId + ". New balance: " + accounts.get(toAccountId));
        return "Amount Transferred " +"remaining balance in both accounts is" +accounts.get(fromAccountId) +" and" +accounts.get(toAccountId);
    }

    public double getBalance(String accountId) {
        if (!accounts.containsKey(accountId)) {
            throw new RuntimeException("Account not found: " + accountId);
        }
        return accounts.get(accountId);
    }
}
