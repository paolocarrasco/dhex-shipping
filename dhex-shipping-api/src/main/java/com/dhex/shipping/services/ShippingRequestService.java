package com.dhex.shipping.services;

import com.dhex.shipping.model.ShippingRequest;

import java.util.List;

public interface ShippingRequestService {
    ShippingRequest register(ShippingRequest shippingRequest);

    List<ShippingRequest> retrieveAll();
}
