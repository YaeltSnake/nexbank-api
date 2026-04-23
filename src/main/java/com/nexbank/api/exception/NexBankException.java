package com.nexbank.api.exception;

// Esta clase define el contrato comun
// todas las excepciones tendran un mensaje y un codigo HTTP
public class NexBankException extends RuntimeException {

    private final int statusCode;

    public NexBankException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
