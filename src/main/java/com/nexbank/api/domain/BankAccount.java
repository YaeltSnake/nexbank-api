package com.nexbank.api.domain;

import com.nexbank.api.enums.AccountStatus;
import com.nexbank.api.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus status;
    private BigDecimal balance;
    private LocalDateTime openedAt;
    private User owner;

}
