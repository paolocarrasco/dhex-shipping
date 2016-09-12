package com.dhex.shipping;

import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingRequestTrack;
import com.dhex.shipping.services.ShippingService;

import java.util.List;

public final class Main {

    private static final ShippingService SHIPPING_SERVICE;

    static {
        SHIPPING_SERVICE = new ShippingService();
    }

    private Main() {
    }

    public static void main(String[] args) {
        if (args == null || args.length < 4) {
            System.exit(-1);
        }

        ShippingRequest shippingRequest = registerShipping(args);
        registerStatus(shippingRequest);
        trackStatus(shippingRequest);
    }

    private static ShippingRequest registerShipping(String[] args) {
        String observations = null;
        if(args.length == 5) {
            observations = args[4];
        }

        ShippingRequest shippingRequest = SHIPPING_SERVICE.registerRequest(args[0], args[1], args[2], Long.valueOf(args[3]), observations);

        System.out.printf("Shipping ID: %s. " +
                        "Registered at %s with a total cost of S/. %s, from %s to %s. " +
                        "To be delivered to the address %s. Comments? %s\n",
                shippingRequest.getId(),
                shippingRequest.getRegistrationMoment().toLocalDateTime(),
                shippingRequest.getSendingCost(),
                shippingRequest.getSender(),
                shippingRequest.getReceiver(),
                shippingRequest.getDestinationAddress(),
                shippingRequest.getObservations());

        return shippingRequest;
    }

    private static void registerStatus(ShippingRequest shippingRequest) {
        String shippingRequestId = shippingRequest.getId();
        SHIPPING_SERVICE.registerStatus(shippingRequestId, "Cajamarca", "In transit", "Everything ok");
        SHIPPING_SERVICE.registerStatus(shippingRequestId, "Trujillo", "In transit", "Everything ok");
        SHIPPING_SERVICE.registerStatus(shippingRequestId, "Trujillo", "Internal", "Package looks broken");
        SHIPPING_SERVICE.registerStatus(shippingRequestId, "Huaraz", "On hold", "Police is inspecting the package");
        SHIPPING_SERVICE.registerStatus(shippingRequestId, "Huaraz", "In transit", "Everything ok");
        SHIPPING_SERVICE.registerStatus(shippingRequestId, "Barranca", "Delivered", "Received by the recipient itself");
    }

    private static void trackStatus(ShippingRequest shippingRequest) {
        final List<ShippingRequestTrack> tracks = SHIPPING_SERVICE.trackStatusOf(shippingRequest.getId());
        tracks.forEach(t -> System.out.printf("Status of the shipping: %s\n", t));
    }

}
