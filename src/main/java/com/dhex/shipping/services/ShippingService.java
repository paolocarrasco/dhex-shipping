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

import static com.dhex.shipping.model.ShippingRequest.createShippingRequest;

public class ShippingService {
    private long sequenceId = 0;
    private List<ShippingRequest> storedShippingRequests = new ArrayList<>();

    public ShippingRequest registerRequest(SendingRequestParameterList sendingRequestParameterList) {
        validateParameters(sendingRequestParameterList);

        String generatedId = generateId(sendingRequestParameterList.getSender());
        double totalCost = obtainTotalCost(sendingRequestParameterList.getSendingCost());

        ShippingRequest shippingRequest = createShippingRequest(
                generatedId, sendingRequestParameterList.getReceiver(),
                sendingRequestParameterList.getSender(), sendingRequestParameterList.getDestinationAddress(),
                totalCost, OffsetDateTime.now());

        addObservations(sendingRequestParameterList.getObservations(), shippingRequest);

        storedShippingRequests.add(shippingRequest);
        return shippingRequest;
    }

    private void validateParameters(SendingRequestParameterList sendingRequestParameterList) {
        // Validate all the objects
        validateParameter(sendingRequestParameterList.getReceiver(), "Receiver");
        validateParameter(sendingRequestParameterList.getSender(), "Sender");
        validateParameter(sendingRequestParameterList.getDestinationAddress(),
                "Destination address");
        if (sendingRequestParameterList.getSendingCost() < 0)
            throw new InvalidArgumentDhexException("Sending cost should be positive");
    }

    private void validateParameter(String receiver, String fieldName) {
        if (receiver == null || receiver.isEmpty())
            throw new InvalidArgumentDhexException(fieldName +
                    " should not be empty");
    }

    private void addObservations(String observations, ShippingRequest shippingRequest) {
        if(observations != null && !observations.isEmpty()) {
            shippingRequest.setObservations(observations);
        }
    }

    private String generateId(String sender) {
        // According to latest changes in the business, ID should be generated according to these rules:
        // - First letter should be the sender name's registered name.
        // - Following 6 letters should be the year (yyyy) and month (mm).
        // - Finally, put the 16 digits of a sequential number.
        String generatedId = sender.substring(0, 1);
        generatedId += String.valueOf(OffsetDateTime.now().getYear());
        generatedId += String.format("%02d", OffsetDateTime.now().getMonth().getValue());
        generatedId += String.format("%016d", ++sequenceId);
        return generatedId;
    }

    private double obtainTotalCost(long sendingCost) {
        double totalCost;
        double cstWithCommssn;
        // According to the business rules,
        // it considers a special commission for each range of amount.
        if (sendingCost >= 20)
            if (sendingCost < 20 || sendingCost >= 100)
                if (sendingCost < 100 || sendingCost >= 300)
                    if (sendingCost < 300 || sendingCost >= 500)
                        if (sendingCost < 500 || sendingCost >= 1000)
                            if (sendingCost < 1000 || sendingCost >= 10000)
                                cstWithCommssn = sendingCost * 1.03;
                            else cstWithCommssn = sendingCost * 1.05;
                        else cstWithCommssn = sendingCost + 50;
                    else cstWithCommssn = sendingCost + 20;
                else cstWithCommssn = sendingCost + 17;
            else cstWithCommssn = sendingCost + 8;
        else cstWithCommssn = sendingCost + 3;
        totalCost = cstWithCommssn * 1.18;
        return totalCost;
    }

    public ShippingStatus registerStatus(String reqId, String loc, String stat, String obs) {
        // Search the shipping request that matches with request ID.
        // Otherwise throws an exception.
        ShippingRequest shipReq = storedShippingRequests.stream()
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
        ShippingRequest shipReq = storedShippingRequests.stream()
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
