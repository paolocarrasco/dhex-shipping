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

    public ShippingRequest(long id, String receiver, String sender, String destinationAddress,
                           double sendingCost, String observations, OffsetDateTime registrationMoment) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.destinationAddress = destinationAddress;
        this.sendingCost = sendingCost;
        this.observations = observations;
        this.registrationMoment = registrationMoment;
    }

    public long getId() {
        return id;
    }


    public OffsetDateTime getRegistrationMoment() {
        return registrationMoment;
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
