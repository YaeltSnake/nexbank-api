package com.nexbank.api.mapper;

import com.nexbank.api.dto.TransactionDTO;
import com.nexbank.api.domain.Transaction;
import com.nexbank.api.dto.TransferDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "accountNumber", source = "account.accountNumber")
    TransactionDTO toDTO(Transaction transaction);


    @Mapping(target = "sourceAccountNumber", source = "account.accountNumber")
    TransferDetailDTO toTransferDetailDTO(Transaction transaction);

}
