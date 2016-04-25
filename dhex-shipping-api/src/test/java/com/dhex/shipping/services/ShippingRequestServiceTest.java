package com.dhex.shipping.services;

import com.dhex.shipping.model.ShippingRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShippingRequestServiceTest {

    private ShippingRequestService shippingRequestService;

    @Before
    public void setUp() throws Exception {
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
}
