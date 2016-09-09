package com.dhex.shipping.service;

import com.dhex.shipping.model.ShippingRequest;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShippingService {
    private long sequenceId = 0;
    private List<ShippingRequest> shippingRequests = new ArrayList<>();

    public void register(ShippingRequest shippingRequest) {
        shippingRequest.setId(++sequenceId);
        shippingRequest.setRegistrationMoment(OffsetDateTime.now());
        shippingRequests.add(shippingRequest);
    }
}
