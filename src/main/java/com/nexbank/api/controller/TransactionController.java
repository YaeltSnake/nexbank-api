package com.nexbank.api.controller;

import com.nexbank.api.controller.request.TransferRequest;
import com.nexbank.api.dto.TransactionDTO;
import com.nexbank.api.dto.TransferDetailDTO;
import com.nexbank.api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<List<TransferDetailDTO>> transfer(@RequestBody @Valid TransferRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.transfer(request));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@PathVariable Long accountId){
        return ResponseEntity.ok(transactionService.getTransactionsByAccount(accountId));
    }
}
