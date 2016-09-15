package com.dhex.shipping.dao;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.Country;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CountryBasicDao implements CountryDao {

    private static final String DUPLICATED_NAME_ERROR_MESSAGE = "Country name %s already exists";
    private Set<Country> countries;
    private long sequentialId;

    public CountryBasicDao() {
        countries = new HashSet<>();
        sequentialId = 0;
    }

    @Override
    public Country insert(String countryName) {
        Country country = new Country(++sequentialId, countryName, true);
        boolean wasAdded = countries.add(country);

        if (!wasAdded) {
            String errorMessage = String.format(DUPLICATED_NAME_ERROR_MESSAGE, country.getName());
            throw new DuplicatedEntityException(errorMessage);
        }

        return country;
    }

}
