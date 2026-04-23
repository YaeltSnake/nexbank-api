package com.nexbank.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexbank.api.enums.AccountStatus;
import com.nexbank.api.enums.AccountType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class BankAccountDetailDTO {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus status;
    private BigDecimal balance;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime openedAt;
    private UserDTO owner;

}
