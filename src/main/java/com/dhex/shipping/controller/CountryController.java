package com.dhex.shipping.controller;

import com.dhex.shipping.model.Country;
import com.dhex.shipping.services.CountryService;

/**
 * Created by avantica on 18/10/2016.
 */
public class CountryController {

    CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    public Country save(String country) {
        return countryService.create(country);
    }
}
