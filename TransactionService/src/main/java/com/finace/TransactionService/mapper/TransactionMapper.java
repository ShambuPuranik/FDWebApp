package com.finace.TransactionService.mapper;

import com.finace.TransactionService.domain.Transaction;
import com.finace.TransactionService.dto.TransactionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO toDto(Transaction entity);

    Transaction toEntity(TransactionDTO dto);

    List<TransactionDTO> toDtoList(List<Transaction> entities);

    List<Transaction> toEntityList(List<TransactionDTO> dtoList);

}
