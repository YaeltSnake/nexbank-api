package com.nexbank.api.service;

import com.nexbank.api.controller.request.CreateAccountRequest;
import com.nexbank.api.controller.request.DepositRequest;
import com.nexbank.api.controller.request.WithdrawalRequest;
import com.nexbank.api.domain.BankAccount;
import com.nexbank.api.domain.Transaction;
import com.nexbank.api.domain.User;
import com.nexbank.api.dto.AccountOperationDTO;
import com.nexbank.api.dto.BankAccountDTO;
import com.nexbank.api.dto.BankAccountDetailDTO;
import com.nexbank.api.enums.AccountStatus;
import com.nexbank.api.enums.TransactionType;
import com.nexbank.api.exception.*;
import com.nexbank.api.mapper.BankAccountMapper;
import com.nexbank.api.repository.BankAccountRepository;
import com.nexbank.api.repository.TransactionRepository;
import com.nexbank.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final BankAccountMapper mapper;

    public AccountService(BankAccountRepository bankAccountRepository,
                          UserRepository userRepository,
                          TransactionRepository transactionRepository,
                          BankAccountMapper mapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }


    public BankAccountDTO createAccount(CreateAccountRequest request){

        User owner = getUser(request.getUserId());

        BankAccount account = BankAccount.builder()
                .accountType(request.getAccountType())
                .balance(request.getInitialBalance())
                .status(AccountStatus.PENDING)
                .owner(owner)
                .openedAt(LocalDateTime.now())
                .accountNumber(generateAccountNumber())
                .build();

        BankAccount saved = bankAccountRepository.save(account);

        return mapper.toDTO(saved);

    }

    @Transactional(readOnly = true)
    public BankAccountDetailDTO getAccountById(Long id){

        BankAccount account = getAccount(id);

        return mapper.toDetailDTO(account);

    }

    public List<BankAccountDTO> getAccountsByUser(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<BankAccount> list = bankAccountRepository.findByOwner(user);

        return list.stream().map(mapper::toDTO).collect(Collectors.toList());

    }

    @Transactional
    public AccountOperationDTO deposit(Long accountId, DepositRequest request){

        LocalDateTime now = LocalDateTime.now();

        validateAmount(request.getAmount());
        BankAccount account = getAccount(accountId);
        isAccountActive(account);

        BigDecimal balanceBefore = account.getBalance();
        account.setBalance(balanceBefore.add(request.getAmount()));

        Transaction tx = saveTransaction(TransactionType.DEPOSIT, account, request.getAmount(), now, "Deposit");

        AccountOperationDTO dto = AccountOperationDTO.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .balanceBefore(balanceBefore)
                .balanceAfter(account.getBalance())
                .operationType(tx.getType())
                .amount(request.getAmount())
                .timestamp(now)
                .build();

        return dto;

    }



    @Transactional
    public AccountOperationDTO withdraw(Long accountId, WithdrawalRequest request){

        LocalDateTime now = LocalDateTime.now();

        validateAmount(request.getAmount());
        BankAccount account = getAccount(accountId);
        isAccountActive(account);
        hasEnoughBalance(account, request.getAmount());

        BigDecimal balanceBefore = account.getBalance();
        account.setBalance(balanceBefore.subtract(request.getAmount()));

        Transaction tx = saveTransaction(TransactionType.WITHDRAWAL, account, request.getAmount(), now, "Withdraw");

        AccountOperationDTO dto = AccountOperationDTO.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .balanceBefore(balanceBefore)
                .balanceAfter(account.getBalance())
                .operationType(tx.getType())
                .amount(request.getAmount())
                .timestamp(now)
                .build();

        return dto;

    }

    @Transactional
    public void blockAllAccountsByUser(User user, AccountStatus newStatus){
        if (newStatus == null) {
            throw new IllegalArgumentException("Account status cannot be null");
        }
        if (newStatus == AccountStatus.ACTIVE) {
            throw new InvalidAccountStatusTransitionException(newStatus);
        }
        List<BankAccount> accounts = bankAccountRepository.findByOwner(user).stream()
                .filter(bankAccount -> bankAccount.getStatus() != AccountStatus.CLOSED)
                .collect(Collectors.toList());
        accounts.forEach(bankAccount -> bankAccount.setStatus(newStatus));
    }

    // metodos extras

    private User getUser(Long userId){
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

    }

    private BankAccount getAccount(Long accountId){
        return bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    private void validateAmount(BigDecimal amount){
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmountException();
        }
    }

    private void isAccountActive(BankAccount account){
        if (account.getStatus() != AccountStatus.ACTIVE){
            throw new AccountNotActiveException(account.getId());
        }
    }

    private void hasEnoughBalance(BankAccount account, BigDecimal amount){

        if (account.getBalance().compareTo(amount) < 0){
            throw new InsufficientFundsException();
        }

    }


    private Transaction saveTransaction(TransactionType type, BankAccount account,
                                        BigDecimal amount, LocalDateTime now, String description){
        Transaction tx = Transaction.builder()
                .type(type)
                .amount(amount)
                .account(account)
                .executedAt(now)
                .description(description)
                .build();

        return transactionRepository.save(tx);

    }

    private String generateAccountNumber(){
        return "NX" + System.currentTimeMillis();
    }

}
