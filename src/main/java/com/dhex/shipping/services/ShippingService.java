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

    public ShippingRequest registerRequest(String receiverName, String senderName, String destinationAddress,
                                           long sendCost, String observations) {
        validateParameters(receiverName, senderName, destinationAddress, sendCost);

        double totalCost = getTotalCost(sendCost);
        String generatedId = generateId(senderName);

        ShippingRequest generatedShippingRequest = generateShippingRequest(
                receiverName, senderName, destinationAddress, observations, totalCost, generatedId);

        shipReqs.add(generatedShippingRequest);
        return generatedShippingRequest;
    }

    private ShippingRequest generateShippingRequest(String receiverName, String senderName, String destinationAddress, String observations, double totalCost, String generatedId) {
        ShippingRequest generatedShippingRequest = new ShippingRequest(
                generatedId, receiverName, senderName, destinationAddress, totalCost, OffsetDateTime.now());
        if(observations != null && !observations.isEmpty()) {
            generatedShippingRequest.setObservations(observations);
        }
        return generatedShippingRequest;
    }

    /*
    According to latest changes in the business, ID should be generated according to these rules:
    First letter should be the sender name's registered name.
    Following 6 letters should be the year (yyyy) and month (mm).
    Finally, put the 16 digits of a sequential number.
    */
    private String generateId(String senderName) {
        String generatedId = senderName.substring(0, 1);
        generatedId += String.valueOf(OffsetDateTime.now().getYear());
        generatedId += String.format("%02d", OffsetDateTime.now().getMonth().getValue());
        generatedId += String.format("%016d", ++sequenceId);
        return generatedId;
    }

    // According to the business rules, it considers a special commission for each range of amount.
    private double getTotalCost(long sendCost) {
        double totalCost;
        double costWithComission;
        if (sendCost < 20) {
            costWithComission = sendCost + 3;
        } else if (sendCost >= 20 && sendCost < 100) {
            costWithComission = sendCost + 8;
        } else if (sendCost >= 100 && sendCost < 300) {
            costWithComission = sendCost + 17;
        } else if (sendCost >= 300 && sendCost < 500) {
            costWithComission = sendCost + 20;
        } else if (sendCost >= 500 && sendCost < 1000) {
            costWithComission = sendCost + 50;
        } else if (sendCost >= 1000 && sendCost < 10000) {
            costWithComission = sendCost * 1.05;
        } else {
            costWithComission = sendCost * 1.03;
        }
        totalCost = costWithComission * 1.18;
        return totalCost;
    }

    private void validateParameters(String receiverName, String senderName, String destinationAddress, long sendCost) {
        // Validate all the objects
        validateParameter(receiverName, "Receiver");
        validateParameter(senderName, "Sender");
        validateParameter(destinationAddress, "Destination address");
        if (sendCost < 0) {
            throw new InvalidArgumentDhexException("Sending cost should be positive");
        }
    }

    private void validateParameter(String parameter, String message) {
        if (parameter == null || parameter.isEmpty())
            throw new InvalidArgumentDhexException(message + " should not be empty");
    }

    public ShippingStatus registerStatus(String requestId, String location, String status, String observations) {
        ShippingRequest shippingRequest = getShippingRequest(requestId);
        ShippingStatus lastStatus = shippingRequest.getLastStatus();
        validateStatus(status, lastStatus);
        ShippingStatus shippingStatus = getShippingStatus(location, status, observations, shippingRequest);
        shippingRequest.addStatus(shippingStatus);
        return shippingStatus;
    }

    // According to the rules of the business, this ID should be conformed of:
    // - Prefix "S".
    // - Followed by the shipping request ID.
    // - Followed by a dash.
    // - And finally the 3 digits of a sequential number for all the statuses for that shipping request.
    private ShippingStatus getShippingStatus(String location, String status, String observations, ShippingRequest shippingRequest) {
        String statusId = "S" + shippingRequest.getId() + "-" + String.format("%03d", shippingRequest.getStatusList().size() + 1);
        return new ShippingStatus(statusId, location, status, OffsetDateTime.now(), observations);
    }

    // Status can be changed from "In transit" or "Internal" to any other status (including "In transit").
    // Status can be changed only from "On hold" to "In transit".
    // Any other status cannot be changed.
    private void validateStatus(String status, ShippingStatus lastStatus) {
        if (lastStatus != null) {
            String inTransitStatus = "in transit";
            String lastStatusFromStatus = lastStatus.getStatus();
            boolean isInternalStatus = lastStatusFromStatus.equalsIgnoreCase("internal");
            boolean isNotInTransitStatus = !lastStatusFromStatus.equalsIgnoreCase(inTransitStatus);
            boolean isOnHoldStatus = lastStatusFromStatus.equalsIgnoreCase("on hold");
            if (isInternalStatus) {
                // This is the case of ShippingRequest that was just created.
            } else if (isNotInTransitStatus
                    && (isOnHoldStatus && !status.equalsIgnoreCase(inTransitStatus)
                    || !isOnHoldStatus)) {
                throw new NotValidShippingStatusException(lastStatusFromStatus, status);
            }
        }
    }

    private ShippingRequest getShippingRequest(String requestId) {
        // Search the shipping request that matches with request ID.
        // Otherwise throws an exception.
        return shipReqs.stream()
                .limit(1)
                .filter(sr -> sr.getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new ShippingNotFoundException(requestId));
    }

    public List<ShippingRequestTrack> trackStatusOf(String requestId) {
        // Search the shipping request that matches with request ID.
        // Otherwise throws an exception.
        ShippingRequest shippingRequest = getShippingRequest(requestId);

        LinkedList<ShippingRequestTrack> tracks = new LinkedList<>();
        // We have to return each status transformed into track
        for (ShippingStatus shippingStatus : shippingRequest.getStatusList()) {
            if (!shippingStatus.getStatus().equalsIgnoreCase("internal")) {
                tracks.add(generateShippingRequestTrack(shippingStatus));
            }
        }
        return tracks;
    }

    private ShippingRequestTrack generateShippingRequestTrack(ShippingStatus shippingStatus) {
        return new ShippingRequestTrack(
                shippingStatus.getLocation(),
                shippingStatus.getMoment().format(DateTimeFormatter.ofPattern("MMM dd'th' 'of' yyyy")),
                shippingStatus.getStatus(),
                shippingStatus.getObservations());
    }
}
