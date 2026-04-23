package com.nexbank.api.repository;

import com.nexbank.api.domain.Transaction;
import com.nexbank.api.enums.TransactionType;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Long id);

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByAccountIdAndType(Long accountId, TransactionType type);

}
