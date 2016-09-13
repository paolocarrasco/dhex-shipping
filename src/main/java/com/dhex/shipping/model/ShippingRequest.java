package com.dhex.shipping.model;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Any request made by a client to ship a thing to another person.
 */
public class ShippingRequest {
    private String id;
    private String receiver;
    private String sender;
    private OffsetDateTime registrationMoment;
    private String destinationAddress;
    private double sendingCost;
    private String observations;
    private LinkedList<ShippingStatus> shippingStatusList;

    private ShippingRequest(String id, String receiver, String sender, String destinationAddress,
                            double sendingCost, OffsetDateTime registrationMoment) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.destinationAddress = destinationAddress;
        this.sendingCost = sendingCost;
        this.registrationMoment = registrationMoment;
        shippingStatusList = new LinkedList<>();
    }

    public static ShippingRequest createShippingRequest(String id, String receiver, String sender, String destinationAddress,
                                                        double sendingCost, OffsetDateTime registrationMoment) {
        return new ShippingRequest(id, receiver, sender, destinationAddress, sendingCost, registrationMoment);
    }

    public String getId() {
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

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public ShippingStatus getLastStatus() {
        return shippingStatusList.peekLast();
    }

    public void addStatus(ShippingStatus shippingStatus) {
        this.shippingStatusList.add(shippingStatus);
    }

    public List<ShippingStatus> getStatusList() {
        return shippingStatusList;
    }
}
