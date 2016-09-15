package com.dhex.shipping.controllers;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.Country;
import com.dhex.shipping.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("countries")
public class CountryController {
    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(method = POST)
    public ResponseEntity create(@RequestBody String countryName) throws URISyntaxException {
        try {
            Country country = countryService.create(countryName);
            return ResponseEntity
                    .created(new URI("/country/" + countryName))
                    .body(country);
        }
        catch (DuplicatedEntityException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
