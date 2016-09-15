package com.dhex.shipping.controllers;

import com.dhex.shipping.dtos.ListOf;
import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.Country;
import com.dhex.shipping.services.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.dhex.shipping.dtos.ListOf.createListOf;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(allowCredentials = "true", value = "*", methods = {GET, POST}, allowedHeaders = "*")
@RequestMapping("/countries")
public class CountryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);
    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Country> create(@RequestBody String countryName) throws URISyntaxException {
        Country country = countryService.create(countryName);
        return ResponseEntity
                .created(new URI("/country/" + countryName))
                .body(country);
    }

    @ExceptionHandler(value = DuplicatedEntityException.class)
    public ResponseEntity handle(DuplicatedEntityException ex) {
        LOGGER.info("Country already existed.", ex);
        return ResponseEntity.badRequest().body("Country is duplicated");
    }

    @RequestMapping(method = GET)
    public ResponseEntity<ListOf<Country>> list() {
        List<Country> countries = countryService.list();
        return ResponseEntity.ok(createListOf(countries));
    }
}
