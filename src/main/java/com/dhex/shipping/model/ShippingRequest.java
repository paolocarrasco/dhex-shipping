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
}
