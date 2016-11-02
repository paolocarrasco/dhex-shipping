package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.Country;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by avantica on 18/10/2016.
 */
public class CountryService {
    private long sequentialId = 0L;

    Set<Country> countrySet = new HashSet<>();

    public Country create(String countryName) {
        Country country = new Country(sequentialId++, countryName, true);
        boolean countryWasAdded = countrySet.add(country);
        if(!countryWasAdded){
            throw new DuplicatedEntityException();
        }
        return country;
    }
}
