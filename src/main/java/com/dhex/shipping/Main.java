package com.dhex.shipping;

import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.service.ShippingService;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        ShippingRequest shippingRequest =
                new ShippingRequest("Juan", "Perez", "Av. Rep. de Panama", 12);
        ShippingService shippingService =
                new ShippingService();

        shippingService.register(shippingRequest);

        System.out.println(shippingRequest.getId());
        System.out.println(shippingRequest.getRegistrationMoment());
    }
}
