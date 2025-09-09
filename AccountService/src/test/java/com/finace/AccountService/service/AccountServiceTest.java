//package com.finace.AccountService.service;
//
//import com.finace.AccountService.AccountRepo;
//import com.finace.AccountService.domain.Account;
//import com.finace.AccountService.dto.AccountDTO;
//import com.finace.AccountService.mapper.AccountMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AccountServiceTest {
//
//    @Mock
//    private AccountRepo accountRepo;
//
//    @Mock
//    private AccountMapper accountMapper;
//
//    @InjectMocks
//    private AccountService accountService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//
//    @Test
//    void testTransferSuccess() {
//        String response = accountService.transfer(500.0, "A", "B");
//        assertTrue(response.contains("Amount Transferred"));
//        assertEquals(4500.0, accountService.getBalance("A"));
//        assertEquals(2500.0, accountService.getBalance("B"));
//    }
//
//    @Test
//    void testGetBalanceAccountNotFound() {
//        RuntimeException ex = assertThrows(RuntimeException.class,
//                () -> accountService.getBalance("X"));
//        assertEquals("Account not found: X", ex.getMessage());
//    }
//
//    // ---------- Repository-backed methods ----------
//    @Test
//    void testCreateAccount() {
//        AccountDTO dto = new AccountDTO();
//        dto.setAccountId("C");
//        dto.setBalance(1000.0);
//
//        Account entity = new Account();
//        entity.setAccountId("C");
//        entity.setBalance(1000.0);
//
//        when(accountMapper.toEntity(dto)).thenReturn(entity);
//        when(accountRepo.save(entity)).thenReturn(entity);
//        when(accountMapper.toDto(entity)).thenReturn(dto);
//
//        AccountDTO result = accountService.createAcount(dto);
//
//        assertEquals("C", result.getAccountId());
//        assertEquals(1000.0, result.getBalance());
//        verify(accountRepo, times(1)).save(entity);
//    }
//
//    @Test
//    void testCreditAmount() {
//        Account account = new Account();
//        account.setAccountId("C");
//        account.setBalance(500.0);
//
//        when(accountRepo.findByAccountId("C")).thenReturn(account);
//
//        String result = accountService.creditAmount("C", 200.0);
//
//        assertEquals("Success", result);
//        assertEquals(700.0, account.getBalance());
//        verify(accountRepo, times(1)).save(account);
//    }
//
//    @Test
//    void testDebitAmountSuccess() {
//        Account account = new Account();
//        account.setAccountId("C");
//        account.setBalance(1000.0);
//
//        when(accountRepo.findByAccountId("C")).thenReturn(account);
//
//        String result = accountService.debitAmount("C", 400.0);
//
//        assertEquals("Balance After debit :600.0", result);
//        assertEquals(600.0, account.getBalance());
//        verify(accountRepo, times(1)).save(account);
//    }
//
//    @Test
//    void testDebitAmountInsufficientBalance() {
//        Account account = new Account();
//        account.setAccountId("C");
//        account.setBalance(100.0);
//
//        when(accountRepo.findByAccountId("C")).thenReturn(account);
//
//        RuntimeException ex = assertThrows(RuntimeException.class,
//                () -> accountService.debitAmount("C", 200.0));
//        assertEquals("Insufficient balance in C", ex.getMessage());
//    }
//}
