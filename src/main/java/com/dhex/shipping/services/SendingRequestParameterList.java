package com.dhex.shipping.services;

public class SendingRequestParameterList {
    private final String receiver;
    private final String sender;
    private final String destinationAddress;
    private final long sendingCost;
    private final String observations;

    public SendingRequestParameterList(String receiver, String sender, String destinationAddress, long sendingCost, String observations) {
        this.receiver = receiver;
        this.sender = sender;
        this.destinationAddress = destinationAddress;
        this.sendingCost = sendingCost;
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

    public long getSendingCost() {
        return sendingCost;
    }

    public String getObservations() {
        return observations;
    }
}
