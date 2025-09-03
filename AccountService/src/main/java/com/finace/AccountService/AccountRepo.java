package com.finace.AccountService;

import com.finace.AccountService.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

    Account findByAccountId(String accountId);
    boolean existsByAccountId(String accountId);
}
