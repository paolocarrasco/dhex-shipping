package com.dhex.shipping.services;

import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingStatus;

import java.util.ArrayList;
import java.util.List;

public class ShippingRequestServiceImpl implements ShippingRequestService {

    private int generatedCode = 0;
    private List<ShippingRequest> shippingRequests = new ArrayList<>();

    @Override
    public ShippingRequest register(ShippingRequest shippingRequest) {
        shippingRequest.setCode(++generatedCode);
        shippingRequests.add(shippingRequest);
        return shippingRequest;
    }

    @Override
    public List<ShippingRequest> retrieveAll() {
        return shippingRequests;
    }

    @Override
    public void updateStatusOf(ShippingRequest shippingRequest, ShippingStatus shippingStatus) {
        shippingRequest.setStatus(shippingStatus.getStatus());
    }

    @Override
    public List<ShippingStatus> track(long code) {
        return null;
    }

}
