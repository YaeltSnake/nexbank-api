//package com.nexbank.api.repository;
//
//import com.nexbank.api.domain.Transaction;
//import com.nexbank.api.enums.TransactionType;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//
//public class TransactionRepositoryMemory implements TransactionRepository{
//
//    private final Map<Long, Transaction> storage = new HashMap<>();
//    private long idCounter = 1;
//
//
//    @Override
//    public Transaction save(Transaction transaction) {
//        if (transaction.getId() == null){
//            transaction.setId(idCounter++);
//        }
//        storage.put(transaction.getId(), transaction);
//        return transaction;
//    }
//
//    @Override
//    public Optional<Transaction> findById(Long id) {
//        return Optional.ofNullable(storage.get(id));
//    }
//
//    @Override
//    public List<Transaction> findByAccountId(Long accountId) {
//        return storage.values().stream()
//                .filter(transaction -> transaction.getAccount().getId().equals(accountId)).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Transaction> findByAccountIdAndType(Long accountId, TransactionType type) {
//        return storage.values().stream().
//                filter(transaction -> transaction.getAccount().getId().equals(accountId) &&
//                        transaction.getType().equals(type)).collect(Collectors.toList());
//    }
//
//}
