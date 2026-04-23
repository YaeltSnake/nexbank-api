package com.nexbank.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexbank.api.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AccountOperationDTO {

    private Long accountId;
    private String accountNumber;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private TransactionType operationType;
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

}
