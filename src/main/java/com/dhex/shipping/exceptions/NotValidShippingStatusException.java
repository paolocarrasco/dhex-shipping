package com.dhex.shipping.exceptions;

public class NotValidShippingStatusException extends RuntimeException {
    public NotValidShippingStatusException(String lastStatus, String updatedStatus) {
        super(String.format("It is not allowed to move from %s to %s", lastStatus, updatedStatus));
    }
}
