package com.nexbank.api.exception;

public class InvalidAmountException extends NexBankException {
    public InvalidAmountException() {
        super("Transaction amount must be greater than zero", 400);
    }
}
