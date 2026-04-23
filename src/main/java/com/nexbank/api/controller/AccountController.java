package com.nexbank.api.controller;

import com.nexbank.api.controller.request.CreateAccountRequest;
import com.nexbank.api.controller.request.DepositRequest;
import com.nexbank.api.controller.request.WithdrawalRequest;
import com.nexbank.api.dto.AccountOperationDTO;
import com.nexbank.api.dto.BankAccountDTO;
import com.nexbank.api.dto.BankAccountDetailDTO;
import com.nexbank.api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> createAccount(
            @RequestBody @Valid CreateAccountRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDetailDTO> getAccountById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccountDTO>> getAccountsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(accountService.getAccountsByUser(userId));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountOperationDTO> deposit(@PathVariable Long id,
            @RequestBody @Valid DepositRequest request){
        return ResponseEntity.ok(accountService.deposit(id, request));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountOperationDTO> withdraw(@PathVariable Long id,
                                                        @RequestBody @Valid WithdrawalRequest request){
        return ResponseEntity.ok(accountService.withdraw(id, request));
    }
}
