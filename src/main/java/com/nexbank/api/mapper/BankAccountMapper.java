package com.nexbank.api.mapper;


import com.nexbank.api.domain.BankAccount;
import com.nexbank.api.dto.BankAccountDTO;
import com.nexbank.api.dto.BankAccountDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface BankAccountMapper {

    BankAccountDTO toDTO(BankAccount account);

    @Mapping(target = "owner", source = "owner")
    BankAccountDetailDTO toDetailDTO(BankAccount account);


}
