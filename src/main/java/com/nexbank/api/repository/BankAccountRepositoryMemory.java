//package com.nexbank.api.repository;
//
//import com.nexbank.api.domain.BankAccount;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//public class BankAccountRepositoryMemory implements BankAccountRepository{
//
//    private final Map<Long, BankAccount> storage = new HashMap<>();
//    private long idCounter = 1;
//
//    @Override
//    public BankAccount save(BankAccount account) {
//        if (account.getId() == null){
//            account.setId(idCounter++);
//        }
//        storage.put(account.getId(), account);
//        return account;
//    }
//
//    @Override
//    public Optional<BankAccount> findById(Long id) {
//        return Optional.ofNullable(storage.get(id));
//    }
//
//    @Override
//    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
//        return storage.values().stream()
//                .filter(account -> account.getAccountNumber()
//                        .equalsIgnoreCase(accountNumber))
//                .findFirst();
//    }
//
//    @Override
//    public List<BankAccount> findByOwnerId(Long userId) {
//        return storage.values()
//                .stream()
//                .filter(account -> account.getOwner()
//                        .getId().equals(userId)).collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean existsByAccountNumber(String accountNumber) {
//        return storage.values().stream()
//                .anyMatch(account -> account.getAccountNumber()
//                        .equalsIgnoreCase(accountNumber));
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        storage.remove(id);
//    }
//}
