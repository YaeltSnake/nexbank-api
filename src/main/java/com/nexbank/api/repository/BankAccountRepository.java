package com.nexbank.api.repository;

import com.nexbank.api.domain.BankAccount;
import com.nexbank.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {



    Optional<BankAccount> findByAccountNumber(String accountNumber);

    List<BankAccount> findByOwner(User owner);

    boolean existsByAccountNumber(String accountNumber);


}
