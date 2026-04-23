package com.nexbank.api.exception;

public class InsufficientFundsException extends NexBankException {
    public InsufficientFundsException() {
        super("Insufficient funds to complete this operation", 422);
    }
}
