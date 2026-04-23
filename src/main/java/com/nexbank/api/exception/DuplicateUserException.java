package com.nexbank.api.exception;

public class DuplicateUserException extends NexBankException {
    public DuplicateUserException(String email) {
        super("A user with email " + email + " already exists", 409);
    }
}
