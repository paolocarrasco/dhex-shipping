package com.dhex.shipping.controllers;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.Country;
import com.dhex.shipping.services.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @Mock
    private CountryService countryService;
    private CountryController controller;

    @Before
    public void setUp() throws Exception {
        controller = new CountryController(countryService);
    }

    @Test
    public void shouldReturn201IfCountryWasCreated() throws URISyntaxException {
        HttpStatus responseStatusCode = controller.create("Chile").getStatusCode();
        assertThat(responseStatusCode, is(HttpStatus.CREATED));
    }

    @Test
    public void shouldReturnTheCountry() throws URISyntaxException {
        when(countryService.create("Chile"))
                .thenReturn(new Country(1L, "Chile", true));
        ResponseEntity<Country> response = controller.create("Chile");
        assertThat(response.getBody().getName(), is("Chile"));
    }

    @Test
    public void shouldReturn400OnDuplicatedEntity() throws URISyntaxException {
        ResponseEntity responseEntity = controller.handle(new DuplicatedEntityException("Any message"));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(responseEntity.getBody(), is("Country is duplicated"));
    }

}
