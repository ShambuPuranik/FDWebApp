package com.finace.AccountService.mapper;

import com.finace.AccountService.domain.Account;
import com.finace.AccountService.dto.AccountDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    private final AccountMapper mapper = new AccountMapperImpl(); // MapStruct generates Impl

    @Test
    void testEntityToDTO() {
        Account account = new Account(1L, "ACC123", 2000.0);
        AccountDTO dto = mapper.toDto(account);

        assertNotNull(dto);
        assertEquals(account.getId(), dto.getId());
        assertEquals(account.getAccountId(), dto.getAccountId());
        assertEquals(account.getBalance(), dto.getBalance());
    }

    @Test
    void testDTOToEntity() {
        AccountDTO dto = new AccountDTO(1L, "ACC123", 2000.0);
        Account account = mapper.toEntity(dto);

        assertNotNull(account);
        assertEquals(dto.getId(), account.getId());
        assertEquals(dto.getAccountId(), account.getAccountId());
        assertEquals(dto.getBalance(), account.getBalance());
    }
}
