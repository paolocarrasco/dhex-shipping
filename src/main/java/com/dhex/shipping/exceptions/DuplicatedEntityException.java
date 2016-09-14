package com.dhex.shipping.exceptions;

public class DuplicatedEntityException extends RuntimeException {
    public DuplicatedEntityException(String message) {
        super(message);
    }

    public DuplicatedEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
