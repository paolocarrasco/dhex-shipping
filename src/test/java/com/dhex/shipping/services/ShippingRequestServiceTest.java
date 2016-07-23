package com.dhex.shipping.services;

import com.dhex.shipping.builders.CountryBuilder;
import com.dhex.shipping.builders.ShippingStatusBuilder;
import com.dhex.shipping.model.Country;
import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingStatus;
import com.dhex.shipping.model.Status;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShippingRequestServiceTest {

    private ShippingRequestService shippingRequestService;

    @Before
    public void setUp() {
        shippingRequestService = new ShippingRequestServiceImpl();
    }

    @Test
    public void testRegisterShouldReturnTheShippingCodeWhenSuccessful() {
        ShippingRequest shippingRequest1 = shippingRequestService.register(new ShippingRequest());
        ShippingRequest shippingRequest2 = shippingRequestService.register(new ShippingRequest());

        assertTrue(shippingRequest1.getCode() > 0);

        assertTrue(shippingRequest2.getCode() > shippingRequest1.getCode());
    }

    @Test
    public void testRegisterShouldAddShippingToListOfShippings() {
        shippingRequestService.register(new ShippingRequest());
        shippingRequestService.register(new ShippingRequest());

        assertEquals(2, shippingRequestService.retrieveAll().size());
    }

    @Test
    public void testUpdateShipmentStatusShouldUpdateShippingRequestWhenSuccessful() {
        Country countryOfStatus = createAPersistentCountry();
        ShippingRequest shippingRequest = shippingRequestService.register(new ShippingRequest());

        ShippingStatus shippingStatus = new ShippingStatusBuilder()
                .setCountry(countryOfStatus)
                .create();

        shippingRequestService.updateStatusOf(shippingRequest, shippingStatus);

        assertEquals(Status.SHIPPED, shippingRequest.currentStatus());

        shippingStatus = new ShippingStatusBuilder()
                .setCountry(countryOfStatus)
                .setDescription("Some vague description")
                .setStatus(Status.DELIVERED)
                .create();

        shippingRequestService.updateStatusOf(shippingRequest, shippingStatus);

        assertEquals(Status.DELIVERED, shippingRequest.currentStatus());
    }

    @Test
    public void testUpdateShipmentStatusShouldThrowAnErrorIfCountryIsNotValid() {

    }

    private Country createAPersistentCountry() {
        CountryService countryService = CountryServiceFactory.create();
        countryService.add(new CountryBuilder().create());
        return countryService.retrieve("Peru");
    }

    @Test @Ignore
    public void testTrackShipmentShouldReturnAllTheUpdatesOfStatus() {
        ShippingRequest shippingRequest = shippingRequestService.register(new ShippingRequest());

        ShippingStatus shippingStatus = new ShippingStatusBuilder() .create();
        shippingRequestService.updateStatusOf(shippingRequest, shippingStatus);

        shippingStatus = new ShippingStatusBuilder().setStatus(Status.DELIVERED).create();
        shippingRequestService.updateStatusOf(shippingRequest, shippingStatus);

        List<ShippingStatus> shipmentMovements = shippingRequestService
                .track(shippingRequest.getCode());

        assertEquals(2, shipmentMovements.size());
        assertEquals(Status.SHIPPED, shipmentMovements.get(0).getStatus());
        assertEquals(Status.DELIVERED, shipmentMovements.get(1).getStatus());
    }
}
