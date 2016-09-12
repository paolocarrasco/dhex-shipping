package com.dhex.shipping;

import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.service.ShippingService;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        if (args == null || args.length < 4) {
            System.exit(-1);
        }

        registerShipping(args);
        registerStatus();
        trackStatus();
        showDefaultMessage();
    }

    private static void registerShipping(String[] args) {
        ShippingService shippingService =
                new ShippingService();

        String observations = null;
        if(args.length == 5) {
            observations = args[4];
        }

        ShippingRequest shippingRequest = shippingService.register(args[0], args[1], args[2], Long.valueOf(args[3]), observations);

        System.out.printf("Shipping ID: %s. Registered at %s with a total cost of S/. %s. Comments? %s",
                shippingRequest.getId(),
                shippingRequest.getRegistrationMoment().toLocalDateTime(),
                shippingRequest.getSendingCost(),
                shippingRequest.getObservations());
    }

    private static void registerStatus() {

    }

    private static void trackStatus() {

    }

    private static void showDefaultMessage() {

    }
}
