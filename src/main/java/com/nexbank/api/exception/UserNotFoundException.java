package com.nexbank.api.exception;

public class UserNotFoundException extends NexBankException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id, 404);
    }
}
