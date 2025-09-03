package com.finace.AccountService.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testAccountEntity() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountId("ACC123");
        account.setBalance(5000.0);

        assertEquals(1L, account.getId());
        assertEquals("ACC123", account.getAccountId());
        assertEquals(5000.0, account.getBalance());
    }

    @Test
    void testAccountEqualsAndHashcode() {
        Account acc1 = new Account(1L, "ACC123", 5000.0);
        Account acc2 = new Account(1L, "ACC123", 5000.0);

        assertEquals(acc1, acc2);
        assertEquals(acc1.hashCode(), acc2.hashCode());
    }

    @Test
    void testAccountToString() {
        Account account = new Account(1L, "ACC123", 5000.0);
        String str = account.toString();
        assertTrue(str.contains("ACC123"));
        assertTrue(str.contains("5000.0"));
    }
}
