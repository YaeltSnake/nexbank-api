package com.nexbank.api.repository;

import com.nexbank.api.domain.Transaction;
import com.nexbank.api.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByAccountIdAndType(Long accountId, TransactionType type);

}
