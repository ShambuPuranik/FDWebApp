package com.finace.TransactionService.service;



import com.finace.TransactionService.client.AccountClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final AccountClient accountClient;

    public TransactionService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }
    @CircuitBreaker(name = "accountService", fallbackMethod = "debitFallback")
    public String debitAccount(String accountId, double amount) {
        return accountClient.debit(accountId, amount);
    }


    // Saga fallback logic
    public String debitFallback(String accountId, double amount, Throwable ex) {
        if (ex instanceof RuntimeException && ex.getMessage().contains("Insufficient")) {
            // Business failure → compensate immediately
            double beforeBalance = accountClient.getBalance();
            // Perform rollback (credit)
            String rollback = accountClient.credit(amount);

            // Fetch balance after rollback
            double afterBalance = accountClient.getBalance();

            // Print log
            System.out.println(" Before rollback balance: " + beforeBalance);
            System.out.println(" After rollback balance: " + afterBalance);

            return " Debit failed: " + ex.getMessage() +
                    " | Compensation applied: " + rollback;
        }
        // Service unavailable → don't retry now, mark pending
        return " Debit failed (service down). Saga will retry later. Reason: " + ex.getMessage();
    }

}

