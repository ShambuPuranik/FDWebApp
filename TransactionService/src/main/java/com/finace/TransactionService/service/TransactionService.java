package com.finace.TransactionService.service;



import com.finace.TransactionService.client.AccountClient;
import com.finace.TransactionService.domain.Transaction;
import com.finace.TransactionService.dto.TransactionDTO;
import com.finace.TransactionService.mapper.TransactionMapper;
import com.finace.TransactionService.repository.TransactionRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final AccountClient accountClient;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }
    @CircuitBreaker(name = "accountService", fallbackMethod = "debitFallback")
    public String debitAccount(String accountId, double amount) {
        return accountClient.debit(accountId, amount);
    }


    // Saga fallback logic
    public String debitFallback(String accountId, double amount, Throwable ex) {
        String insufficientMsg = "Insufficient balance in " + accountId;
        if (ex instanceof RuntimeException && ex.getMessage().startsWith(insufficientMsg)) {
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

    public String transferAmount(String fromAcc, String toAcc, double amount){
        if(accountClient.debitAmount(fromAcc,amount).startsWith("Balance After debit")){
            accountClient.creditAmount(toAcc,amount);
            return "Success";
        }else return "transfer Failed";

    }

    public String transferAmounts(TransactionDTO transactionDTO){

        if(accountClient.debitAmount(transactionDTO.getFromAcc(),transactionDTO.getAmount()).startsWith("Balance After debit")){
            accountClient.creditAmount(transactionDTO.getToAcc(),transactionDTO.getAmount());

           Transaction transaction = transactionMapper.toEntity(transactionDTO);
           transaction.setAmount(transactionDTO.getAmount());
           transaction.setFromAcc(transactionDTO.getFromAcc());
           transaction.setToAcc(transactionDTO.getToAcc());
            transactionRepo.save(transaction);
            return "Success";
        }else return "transfer Failed";

    }

}

