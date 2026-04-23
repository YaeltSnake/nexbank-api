package com.nexbank.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Ctr + F9(Build Project)
@Getter
@Builder
public class TransferDetailDTO {

    private Long id;
    private BigDecimal amount;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private LocalDateTime executedAt;

}
