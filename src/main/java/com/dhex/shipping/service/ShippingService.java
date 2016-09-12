package com.dhex.shipping.service;

import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.model.ShippingRequest;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShippingService {
    private long sequenceId = 0;
    private List<ShippingRequest> shippingRequests = new ArrayList<>();

    public ShippingRequest register(String receiver, String sender, String destinationAddress, long sendingCost, String observations) {
        double totalCost;
        // Validate all the objects
        if (receiver == null || receiver.isEmpty()) {
            throw new InvalidArgumentDhexException("Receiver should not be empty");
        }
        if (sender == null || sender.isEmpty()) {
            throw new InvalidArgumentDhexException("Sender should not be empty");
        }
        if (destinationAddress == null || destinationAddress.isEmpty()) {
            throw new InvalidArgumentDhexException("Destination address should not be empty");
        }
        if (sendingCost < 0) {
            throw new InvalidArgumentDhexException("Sending cost should be positive");
        }
        else {
            double costWithCommission;
            if (sendingCost < 20) {
                costWithCommission = sendingCost + 3;
            }
            else if (sendingCost >= 20 && sendingCost < 100) {
                costWithCommission = sendingCost + 8;
            }
            else if (sendingCost >= 100 && sendingCost < 300) {
                costWithCommission = sendingCost + 17;
            }
            else if (sendingCost >= 300 && sendingCost < 500) {
                costWithCommission = sendingCost + 20;
            }
            else if (sendingCost >= 500 && sendingCost < 1000) {
                costWithCommission = sendingCost + 50;
            }
            else if (sendingCost >= 1000 && sendingCost < 10000) {
                costWithCommission = sendingCost * 1.05;
            }
            else {
                costWithCommission = sendingCost * 1.03;
            }
            totalCost = costWithCommission * 1.18;
        }
        // According to latest changes in the business, ID should be generated according to these rules:
        // - First letter should be the sender name's registered name.
        // - Following 6 letters should be the year (yyyy) and month (mm).
        // - Finally, put the 16 digits of a sequential number.
        String generatedId = sender.substring(0, 1);
        generatedId += String.valueOf(OffsetDateTime.now().getYear());
        generatedId += String.format("%02d", OffsetDateTime.now().getMonth().getValue());
        generatedId += String.format("%016d", ++sequenceId);
        ShippingRequest shippingRequest = new ShippingRequest(
                generatedId, receiver, sender, destinationAddress, totalCost, OffsetDateTime.now());
        if(observations != null && !observations.isEmpty()) {
            shippingRequest.setObservations(observations);
        }

        shippingRequests.add(shippingRequest);
        return shippingRequest;
    }
}
