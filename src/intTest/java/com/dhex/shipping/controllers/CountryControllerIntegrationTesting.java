package com.dhex.shipping.controllers;

import com.dhex.shipping.Application;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryControllerIntegrationTesting {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturn201WhenCountryIsCreated() {
        createCountry("Peru").then()
                .assertThat()
                .statusCode(CREATED.value())
                .header("Location", "/country/Peru")
                .body("name", is("Peru"));
    }

    @Test
    public void shouldReturn400WhenCountryAlreadyExists() {
        createCountry("Brasil");
        createCountry("Brasil").then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void shouldReturn200WhenCountriesAreListed() {
        createCountry("Colombia");
        createCountry("Ecuador");
        createCountry("Venezuela");

        when()
                .get("/countries")
        .then()
                .assertThat()
                .statusCode(OK.value())
            .and()
                .body("list.name", hasItems("Venezuela", "Ecuador", "Colombia"));
    }

    private Response createCountry(String countryName) {
        return given()
                .body(countryName)
        .when()
                .post("/countries");

    }
}
