package com.nexbank.api.domain;

import com.nexbank.api.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private BankAccount account;
    private String targetAccountNumber;  // referencia al otro lado
    private LocalDateTime executedAt;
    private String description;

}
