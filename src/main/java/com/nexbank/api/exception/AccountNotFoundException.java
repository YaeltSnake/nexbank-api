package com.nexbank.api.exception;

public class AccountNotFoundException extends NexBankException {
    public AccountNotFoundException(Long id) {
        super("Account not found with id: " + id, 404);
    }
}
