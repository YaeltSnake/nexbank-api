package com.nexbank.api.repository;

import com.nexbank.api.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {



    Optional<BankAccount> findByAccountNumber(String accountNumber);

    List<BankAccount> findByOwnerId(Long userId);

    boolean existsByAccountNumber(String accountNumber);


}
