package com.dhex.shipping.model;

import java.time.OffsetDateTime;

/**
 * Any request made by a client to ship a thing to another person.
 */
public class ShippingRequest {
    private long id;
    private String receiver;
    private String sender;
    private OffsetDateTime registrationMoment;
    private String destinationAddress;
    private double sendingCost;
    private String observations;

    public ShippingRequest(String receiver, String sender, String destinationAddress,
                           double sendingCost) {
        this.receiver = receiver;
        this.sender = sender;
        this.destinationAddress = destinationAddress;
        this.sendingCost = sendingCost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRegistrationMoment(OffsetDateTime registrationMoment) {
        this.registrationMoment = registrationMoment;
    }

    public OffsetDateTime getRegistrationMoment() {
        return registrationMoment;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public double getSendingCost() {
        return sendingCost;
    }

    public String getObservations() {
        return observations;
    }

}
