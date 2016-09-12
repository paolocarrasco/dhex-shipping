package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.exceptions.NotValidShippingStatusException;
import com.dhex.shipping.exceptions.ShippingNotFoundException;
import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingRequestTrack;
import com.dhex.shipping.model.ShippingStatus;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
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

    public ShippingStatus registerStatus(String reqId, String loc, String stat, String obs) {
        // Search the shipping request that matches with request ID.
        // Otherwise throws an exception.
        ShippingRequest shipReq = shipReqs.stream()
                .limit(1)
                .filter(sr -> sr.getId().equals(reqId))
                .findFirst()
                .orElseThrow(() -> new ShippingNotFoundException(reqId));
        ShippingStatus lastStatus = shipReq.getLastStatus();
        // Status can be changed from "In transit" or "Internal" to any other status (including "In transit").
        // Status can be changed only from "On hold" to "In transit".
        // Any other status cannot be changed.
        if(lastStatus == null || lastStatus.getStatus().equalsIgnoreCase("internal")) {
            // This is the case of ShippingRequest that was just created.
        }
        else if(!lastStatus.getStatus().equalsIgnoreCase("in transit")) {
            if(lastStatus.getStatus().equalsIgnoreCase("on hold") && !stat.equalsIgnoreCase("in transit"))
                throw new NotValidShippingStatusException(lastStatus.getStatus(), stat);
            else if(!lastStatus.getStatus().equalsIgnoreCase("on hold"))
                throw new NotValidShippingStatusException(lastStatus.getStatus(), stat);
        }
        // According to the rules of the business, this ID should be conformed of:
        // - Prefix "S".
        // - Followed by the shipping request ID.
        // - Followed by a dash.
        // - And finally the 3 digits of a sequential number for all the statuses for that shipping request.
        String statusId = "S" + shipReq.getId() + "-" + String.format("%03d", shipReq.getStatusList().size() + 1);
        ShippingStatus shipStat = new ShippingStatus(statusId, loc, stat, OffsetDateTime.now(), obs);
        shipReq.addStatus(shipStat);
        return shipStat;
    }

    public List<ShippingRequestTrack> trackStatusOf(String reqId) {
        // Search the shipping request that matches with request ID.
        // Otherwise throws an exception.
        ShippingRequest shipReq = shipReqs.stream()
                .limit(1)
                .filter(sr -> sr.getId().equals(reqId))
                .findFirst()
                .orElseThrow(() -> new ShippingNotFoundException(reqId));

        LinkedList<ShippingRequestTrack> tracks = new LinkedList<>();
        // We have to return each status transformed into track
        for (ShippingStatus stat : shipReq.getStatusList()) {
            if(stat.getStatus().equalsIgnoreCase("internal")) {
                continue;
            } else {
                tracks.add(new ShippingRequestTrack(
                        stat.getLocation(),
                        stat.getMoment().format(DateTimeFormatter.ofPattern("MMM dd'th' 'of' yyyy")),
                        stat.getStatus(),
                        stat.getObservations()));
            }
        }
        return tracks;
    }
}
