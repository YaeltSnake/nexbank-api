package com.nexbank.api.service;

import com.nexbank.api.controller.request.TransferRequest;
import com.nexbank.api.domain.BankAccount;
import com.nexbank.api.domain.Transaction;
import com.nexbank.api.dto.BankAccountDTO;
import com.nexbank.api.dto.TransactionDTO;
import com.nexbank.api.dto.TransferDetailDTO;
import com.nexbank.api.enums.AccountStatus;
import com.nexbank.api.enums.TransactionType;
import com.nexbank.api.exception.AccountNotActiveException;
import com.nexbank.api.exception.AccountNotFoundException;
import com.nexbank.api.exception.InsufficientFundsException;
import com.nexbank.api.exception.InvalidAmountException;
import com.nexbank.api.mapper.TransactionMapper;
import com.nexbank.api.repository.BankAccountRepository;
import com.nexbank.api.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionMapper mapper;

    public TransactionService(TransactionRepository transactionRepository,
                              BankAccountRepository bankAccountRepository,
                              TransactionMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.mapper = mapper;
    }

    public List<TransferDetailDTO> transfer(TransferRequest request){

        validateAmount(request.getAmount());

        BankAccount sourceAccount = getAccount(request.getSourceAccountId());
        BankAccount targetAccount = getAccount(request.getTargetAccountId());

        isAccountActive(sourceAccount);
        isAccountActive(targetAccount);

        hasEnoughBalance(sourceAccount, request.getAmount());

        substractSourceBalance(sourceAccount, request.getAmount());
        addTargetBalance(targetAccount, request.getAmount());

        TransferDetailDTO sourceDTO = registerTransferOut(sourceAccount, targetAccount, request.getAmount(), "Transfer-Out");
        TransferDetailDTO targetDTO = registerTransferIn(targetAccount, sourceAccount, request.getAmount(), "Transfer -In");

        return new ArrayList<TransferDetailDTO>(Arrays.asList(sourceDTO,targetDTO));

    }

    public List<TransactionDTO> getTransactionsByAccount(Long accountId){
        BankAccount account = getAccount(accountId);
        return transactionRepository
                .findByAccountId(account.getId())
                .stream().map(mapper::toDTO).collect(Collectors.toList());
    }


    // metodos extras

    private BankAccount getAccount(Long accountId){
        return bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    private void hasEnoughBalance(BankAccount account, BigDecimal amount){
        if (account.getBalance().compareTo(amount) < 0){
            throw new InsufficientFundsException();
        }
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

    private TransferDetailDTO registerTransferOut(BankAccount account,
                                                 BankAccount accountReference,
                                                 BigDecimal amount, String message){

        Transaction.TransactionBuilder transactionBuilder = Transaction.builder();
        transactionBuilder.type(TransactionType.TRANSFER_OUT);
        transactionBuilder.amount(amount);
        transactionBuilder.account(account);
        transactionBuilder.targetAccountNumber(accountReference.getAccountNumber());
        transactionBuilder.description(message);
        transactionBuilder.executedAt(LocalDateTime.now());

        Transaction transaction = transactionBuilder.build();

        transactionRepository.save(transaction);

        return mapper.toTransferDetailDTO(transaction);

    }

    private TransferDetailDTO registerTransferIn(BankAccount account,
                                                BankAccount accountReference,
                                                BigDecimal amount, String message){

        Transaction.TransactionBuilder transactionBuilder = Transaction.builder();
        transactionBuilder.type(TransactionType.TRANSFER_IN);
        transactionBuilder.amount(amount);
        transactionBuilder.account(account);
        transactionBuilder.targetAccountNumber(accountReference.getAccountNumber());
        transactionBuilder.description(message);
        transactionBuilder.executedAt(LocalDateTime.now());

        Transaction transaction = transactionBuilder.build();

        transactionRepository.save(transaction);

        return mapper.toTransferDetailDTO(transaction);

    }


    private void substractSourceBalance(BankAccount account, BigDecimal amount){
        account.setBalance(account.getBalance().subtract(amount));
    }

    private void addTargetBalance(BankAccount account, BigDecimal amount){
        account.setBalance(account.getBalance().add(amount));
    }

}
