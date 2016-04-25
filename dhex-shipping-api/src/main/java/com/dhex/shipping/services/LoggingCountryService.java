package com.dhex.shipping.services;

import com.dhex.shipping.model.Country;

import java.util.logging.Logger;

public class LoggingCountryService extends CountryServiceImpl {
    private static final Logger LOGGER = Logger.getLogger(LoggingCountryService.class.getCanonicalName());

    @Override
    public void add(Country country) {
        String countryName = country == null ? "nulo" : country.getName();
        LOGGER.info("Start adding a country with name: " + countryName);

        super.add(country);

        LOGGER.info("Finish adding a country with name: " + country.getName());
    }
}
