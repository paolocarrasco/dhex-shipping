package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.NotValidShippingStatusException;
import com.dhex.shipping.exceptions.ShippingNotFoundException;
import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class ShippingServiceTest {

    ShippingRequest shippingRequest;

    private ShippingService shippingService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        shippingService = new ShippingService();
        shippingRequest = shippingService.registerRequest(new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 133, null));
    }

    @Test
    public void shouldReturnAnIdAndDateWhenRegistering() {
        ShippingRequest shippingRequest = new ShippingService()
                .registerRequest(new SendingRequestParameterList("Anakin", "Yoda", "Tatooine", 50, null));

        assertThat(shippingRequest.getId(), is(notNullValue()));
        assertThat(shippingRequest.getRegistrationMoment(), is(notNullValue()));
    }


    @Test
    public void shouldReturnAndIdAndRegistryTimeWhenRegisteringNewShipmentStatusAndParammeterListIsComplete() {
        ShippingStatus inShipmentStatus = shippingService.registerStatus(shippingRequest.getId(), "Tatooine", "Delivered", "Anakin is waiting for him new iPhone");

        assertThat(inShipmentStatus.getId(), is(notNullValue()));
        assertThat(inShipmentStatus.getMoment(), is(notNullValue()));
    }

    @Test
    public void shouldThrowAndExceptionWhenMandatoryFieldShippingRequestIdIsMissing() {
        expectedException.expect(ShippingNotFoundException.class);

        shippingService.registerStatus(null, "Tatooine", "Delivered", "Anakin is waiting for him new iPhone");
    }

    @Test
    public void shouldThrowAndExceptionWhenMandatoryFieldActualLocationIsMissing() {
        expectedException.expect(ShippingNotFoundException.class);

        shippingService.registerStatus(shippingRequest.getId(), null, "Delivered", "Anakin is waiting for him new iPhone");
    }

    @Test
    public void shouldThrowAndExceptionWhenMandatoryFieldShipmentStatusIsMissing() {
        expectedException.expect(ShippingNotFoundException.class);

        shippingService.registerStatus(shippingRequest.getId(), "Tatooine", null, "Anakin is waiting for him new iPhone");
    }

    @Test
    public void shouldThrowAndExceptionWhenMandatoryFieldShipmentStatusIsIncorrect() {
        expectedException.expect(NotValidShippingStatusException.class);

        shippingService.registerStatus(shippingRequest.getId(), "Tatooine", "Waiting", "Anakin is waiting for him new iPhone");
    }

    @Test
    public void shouldReturnAndIdAndRegistryTimeWhenRegisteringNewShipmentStatusWithoutObservations() throws Exception {
        ShippingStatus inShipmentStatus = shippingService.registerStatus(shippingRequest.getId(), "Tatooine", "Delivered", null);

        assertThat(inShipmentStatus.getId(), is(notNullValue()));
        assertThat(inShipmentStatus.getMoment(), is(notNullValue()));
    }

}