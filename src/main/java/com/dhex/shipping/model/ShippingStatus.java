package com.dhex.shipping.model;

import java.time.OffsetDateTime;

/**
 * Status of the shipping: where it is, what is its state at that moment.
 */
public class ShippingStatus {
    private long id;
    private String place;
    private OffsetDateTime moment;
    private String state;
    private String observations;
}
