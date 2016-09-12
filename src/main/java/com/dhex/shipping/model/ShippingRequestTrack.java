package com.dhex.shipping.model;

/**
 * It is an indicator for end-users to track the status of their shipping.
 */
public class ShippingRequestTrack {
    private String location;
    private String moment;
    private String status;
    private String observations;

    public ShippingRequestTrack(String location, String moment, String status, String observations) {
        this.location = location;
        this.moment = moment;
        this.status = status;
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "Located in " + location +
                ", at " + moment +
                ", with status of " + status +
                (observations == null ?
                        "." :
                        String.format(", and the following observations: %s.", observations));
    }
}
