package com.nexbank.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexbank.api.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class TransactionDTO {

    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private String accountNumber;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime executedAt;

}
