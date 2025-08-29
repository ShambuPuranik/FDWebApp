package com.finace.TransactionService.repository;

import com.finace.TransactionService.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
