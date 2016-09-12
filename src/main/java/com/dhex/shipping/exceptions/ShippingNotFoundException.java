package com.dhex.shipping.exceptions;

public class ShippingNotFoundException extends RuntimeException {
    public ShippingNotFoundException(String s) {
        super(String.format("Shipping Request with ID %s not found", s));
    }
}
