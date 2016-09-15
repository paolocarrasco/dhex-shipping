package com.dhex.shipping.controllers;

import com.dhex.shipping.Application;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
public class CountryControllerIntegrationTesting {
    @LocalServerPort
    private int port;

    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturn201WhenCountryIsCreated() {
        given()
                .body("Peru")
        .when()
                .post("/countries")
        .then()
                .assertThat()
                .statusCode(CREATED.value())
                .header("Location", "/country/Peru")
                .body("name", is("Peru"));
    }
}
