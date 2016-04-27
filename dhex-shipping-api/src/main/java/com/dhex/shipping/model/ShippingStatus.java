package com.dhex.shipping.model;

public class ShippingStatus {
    private Status status;
    private final Country countryOfStatus;
    private String description;

    public ShippingStatus(Status status, Country countryOfStatus, String description) {
        this.status = status;
        this.countryOfStatus = countryOfStatus;
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

}
