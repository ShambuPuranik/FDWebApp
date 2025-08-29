package com.finace.AccountService.mapper;


import com.finace.AccountService.domain.Account;
import com.finace.AccountService.dto.AccountDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDto(Account entity);

    Account toEntity(AccountDTO dto);

    List<AccountDTO> toDtoList(List<Account> entities);

    List<Account> toEntityList(List<AccountDTO> dtoList);
}
