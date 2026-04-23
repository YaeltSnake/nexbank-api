package com.nexbank.api.exception;

public class AccountNotActiveException extends NexBankException {
    public AccountNotActiveException(Long id) {
        super("Account " + id + " is not active", 422);
    }
}
