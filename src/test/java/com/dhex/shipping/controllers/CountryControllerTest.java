package com.dhex.shipping.controllers;

import com.dhex.shipping.builders.CountryBuilder;
import com.dhex.shipping.dtos.ListOf;
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
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

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

        assertThat(responseStatusCode, is(CREATED));
    }

    @Test
    public void shouldReturnTheCountry() throws URISyntaxException {
        when(countryService.create("Chile"))
                .thenReturn(CountryBuilder.create().with(c -> c.name = "Chile").now());

        ResponseEntity<Country> response = controller.create("Chile");

        assertThat(response.getBody().getName(), is("Chile"));
    }

    @Test
    public void shouldReturn400OnDuplicatedEntity() throws URISyntaxException {
        ResponseEntity responseEntity = controller.handle(new DuplicatedEntityException("Any message"));

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getBody(), is("Country is duplicated"));
    }

    @Test
    public void shouldReturn200WhenListing() {
        ResponseEntity responseEntity = controller.list();

        assertThat(responseEntity.getStatusCode(), is(OK));
    }

    @Test
    public void shouldReturnAListOfCountriesWhenListing() {
        List<Country> expectedCountries = asList(
                CountryBuilder.create().with(c -> c.name = "Brasil").now(),
                CountryBuilder.create().with(c -> c.name = "Argentina").now()
        );
        when(countryService.list())
                .thenReturn(expectedCountries);

        ResponseEntity<ListOf<Country>> responseEntity = controller.list();

        List<Country> countries = responseEntity.getBody().getList();
        assertThat(countries, is(expectedCountries));
    }
}
