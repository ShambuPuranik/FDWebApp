package com.finace.AccountService.controller;

import com.finace.AccountService.dto.AccountDTO;
import com.finace.AccountService.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

//    @Test
//    void testDebit() throws Exception {
//        when(accountService.debit("123", 100.0)).thenReturn("Debited");
//        mockMvc.perform(post("/account/debit/123/100"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Debited"));
//    }
//
//    @Test
//    void testCredit() throws Exception {
//        when(accountService.credit("123", 200.0)).thenReturn("Credited");
//        mockMvc.perform(post("/account/credit/123/200"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Credited"));
//    }

    @Test
    void testGetBalance() throws Exception {
        when(accountService.getBalance("123")).thenReturn(500.0);
        mockMvc.perform(get("/account/balance/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("500.0"));
    }

    @Test
    void testTransfer() throws Exception {
        when(accountService.transfer(100.0, "123", "456")).thenReturn("Transferred");
        mockMvc.perform(post("/account/transfer/100/123/456"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transferred"));
    }

//    @Test
//    void testCreateAccount() throws Exception {
//        AccountDTO dto = new AccountDTO(23L, "Test User", 1000.0);
//        when(accountService.createAcount(dto)).thenReturn(dto);
//
//        mockMvc.perform(post("/account/createAccount")
//                        .contentType("application/json")
//                        .content("{\"accountId\":\"123\",\"name\":\"Test User\",\"balance\":1000.0}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accountId").value("123"))
//                .andExpect(jsonPath("$.name").value("Test User"))
//                .andExpect(jsonPath("$.balance").value(1000.0));
//    }

    @Test
    void testCreditAmount() throws Exception {
        when(accountService.creditAmount("123", 150.0)).thenReturn("Amount Credited");
        mockMvc.perform(post("/account/creditAmount/123/150"))
                .andExpect(status().isOk())
                .andExpect(content().string("Amount Credited"));
    }

    @Test
    void testDebitAmount() throws Exception {
        when(accountService.debitAmount("123", 50.0)).thenReturn("Amount Debited");
        mockMvc.perform(post("/account/debitAmount/123/50"))
                .andExpect(status().isOk())
                .andExpect(content().string("Amount Debited"));
    }
}
