package com.dhex.shipping.services;

import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingStatus;

import java.util.List;

public interface ShippingRequestService {
    ShippingRequest register(ShippingRequest shippingRequest);

    List<ShippingRequest> retrieveAll();

    void updateStatusOf(ShippingRequest shippingRequest, ShippingStatus shippingStatus);

    List<ShippingStatus> track(long code);
}
