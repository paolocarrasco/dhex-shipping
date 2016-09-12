package com.dhex.shipping;

import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.service.ShippingService;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        ShippingService shippingService =
                new ShippingService();

        ShippingRequest shippingRequest = shippingService.register("Juan", "Perez", "Av. Rep. de Panama", 200, null);

        System.out.println(shippingRequest.getId());
        System.out.println(shippingRequest.getRegistrationMoment());
        System.out.println(shippingRequest.getSendingCost());
        System.out.println(shippingRequest.getObservations());
    }
}
