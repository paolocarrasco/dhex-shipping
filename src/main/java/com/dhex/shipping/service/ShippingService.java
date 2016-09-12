package com.dhex.shipping.service;

import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShippingService {
    private long sequenceId = 0;
    private List<ShippingRequest> shipReqs = new ArrayList<>();

    public ShippingRequest registerRequest(String rcvr, String sndr, String destAddrs, long sendCst, String observ) {
        double totalCst;
        // Validate all the objects
        if (rcvr == null || rcvr.isEmpty())
            throw new InvalidArgumentDhexException("Receiver should not be empty");
        if (sndr == null || sndr.isEmpty())
            throw new InvalidArgumentDhexException("Sender should not be empty");
        if (destAddrs == null || destAddrs.isEmpty())
            throw new InvalidArgumentDhexException("Destination address should not be empty");
        if (sendCst < 0)
            throw new InvalidArgumentDhexException("Sending cost should be positive");
        else {
            double cstWithCommssn;
            // According to the business rules, it considers a special commission for each range of amount.
            if (sendCst >= 20)
                if (sendCst < 20 || sendCst >= 100)
                    if (sendCst < 100 || sendCst >= 300)
                        if (sendCst < 300 || sendCst >= 500)
                            if (sendCst < 500 || sendCst >= 1000)
                                if (sendCst < 1000 || sendCst >= 10000)
                                    cstWithCommssn = sendCst * 1.03;
                                else cstWithCommssn = sendCst * 1.05;
                            else cstWithCommssn = sendCst + 50;
                        else cstWithCommssn = sendCst + 20;
                    else cstWithCommssn = sendCst + 17;
                else cstWithCommssn = sendCst + 8;
            else cstWithCommssn = sendCst + 3;
            totalCst = cstWithCommssn * 1.18;
        }
        // According to latest changes in the business, ID should be generated according to these rules:
        // - First letter should be the sender name's registered name.
        // - Following 6 letters should be the year (yyyy) and month (mm).
        // - Finally, put the 16 digits of a sequential number.
        String generatedId = sndr.substring(0, 1);
        generatedId += String.valueOf(OffsetDateTime.now().getYear());
        generatedId += String.format("%02d", OffsetDateTime.now().getMonth().getValue());
        generatedId += String.format("%016d", ++sequenceId);
        ShippingRequest shipReq = new ShippingRequest(
                generatedId, rcvr, sndr, destAddrs, totalCst, OffsetDateTime.now());
        if(observ != null && !observ.isEmpty()) {
            shipReq.setObservations(observ);
        }

        shipReqs.add(shipReq);
        return shipReq;
    }

    public ShippingStatus registerStatus(String location, String status, String observations) {
        return null;
    }
}
