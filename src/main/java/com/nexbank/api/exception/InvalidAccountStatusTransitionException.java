package com.nexbank.api.exception;

import com.nexbank.api.enums.AccountStatus;

public class InvalidAccountStatusTransitionException extends NexBankException{
    public InvalidAccountStatusTransitionException(AccountStatus invalidStatus) {
        super("Account status transition to " + invalidStatus.name() +
                " is not allowed in this operation.", 400);
    }
}
