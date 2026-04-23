package com.nexbank.api.repository;

import com.nexbank.api.domain.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {

    BankAccount save(BankAccount account);

    Optional<BankAccount> findById(Long id);

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    List<BankAccount> findByOwnerId(Long userId);

    boolean existsByAccountNumber(String accountNumber);

    void deleteById(Long id);

}
