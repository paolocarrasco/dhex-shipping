package com.dhex.shipping.model;

import java.time.OffsetDateTime;

/**
 * Status of the shipping: where it is, what is its state at that moment.
 */
public class ShippingStatus {
    private String id;
    private String location;
    private OffsetDateTime moment;
    private String status;
    private String observations;

    public ShippingStatus(String id, String location, String status, OffsetDateTime moment, String observations) {
        this.id = id;
        this.location = location;
        this.status = status;
        this.moment = moment;
        this.observations = observations;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public OffsetDateTime getMoment() {
        return moment;
    }

    public String getObservations() {
        return observations;
    }
}
