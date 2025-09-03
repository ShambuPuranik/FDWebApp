package com.finace.AccountService.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDTOTest {

    @Test
    void testAccountDTOSettersAndGetters() {
        AccountDTO dto = new AccountDTO();
        dto.setId(1L);
        dto.setAccountId("ACC123");
        dto.setBalance(1000.0);

        assertEquals(1L, dto.getId());
        assertEquals("ACC123", dto.getAccountId());
        assertEquals(1000.0, dto.getBalance());
    }

    @Test
    void testAccountDTOEqualsAndHashcode() {
        AccountDTO dto1 = new AccountDTO(1L, "ACC123", 1000.0);
        AccountDTO dto2 = new AccountDTO(1L, "ACC123", 1000.0);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testAccountDTOToString() {
        AccountDTO dto = new AccountDTO(1L, "ACC123", 1000.0);
        String str = dto.toString();
        assertTrue(str.contains("ACC123"));
        assertTrue(str.contains("1000.0"));
    }
}
