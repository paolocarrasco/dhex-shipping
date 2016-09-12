package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.exceptions.NotValidShippingStatusException;
import com.dhex.shipping.exceptions.ShippingNotFoundException;
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

    public ShippingStatus registerStatus(String reqId, String location, String status, String observations) {
        // Search the shipping request that matches with request ID.
        // Otherwise throws an exception.
        ShippingRequest shippingRequest = shipReqs.stream()
                .limit(1)
                .filter(sr -> sr.getId().equals(reqId))
                .findFirst()
                .orElseThrow(() -> new ShippingNotFoundException(reqId));
        final ShippingStatus lastStatus = shippingRequest.getLastStatus();
        // Status can be changed from "In transit" to any other status (including "In transit").
        // Status can be changed only from "On hold" to "In transit".
        // Any other status cannot be changed.
        if(lastStatus == null) {
            // This is the case of ShippingRequest that was just created.
        }
        else if(!lastStatus.getStatus().equalsIgnoreCase("in transit")) {
            if(lastStatus.getStatus().equalsIgnoreCase("on hold") && !status.equalsIgnoreCase("in transit"))
                throw new NotValidShippingStatusException(lastStatus.getStatus(), status);
            else if(!lastStatus.getStatus().equalsIgnoreCase("on hold"))
                throw new NotValidShippingStatusException(lastStatus.getStatus(), status);
        }
        // According to the rules of the business, this ID should be conformed of:
        // - Prefix "S".
        // - Followed by the shipping request ID.
        // - Followed by a dash.
        // - And finally the 3 digits of a sequential number for all the statuses for that shipping request.
        final String statusId = "S" + shippingRequest.getId() + "-" + String.format("%03d", shippingRequest.getStatusList().size() + 1);
        final ShippingStatus shippingStatus = new ShippingStatus(statusId, location, status, OffsetDateTime.now(), observations);
        shippingRequest.addStatus(shippingStatus);
        return shippingStatus;
    }
}
