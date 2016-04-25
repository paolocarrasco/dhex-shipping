package com.dhex.shipping.services;

import com.dhex.shipping.model.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountryServiceImpl implements CountryService {

    private List<Country> countries = new ArrayList<>();

    CountryServiceImpl() {
    }

    @Override
    public List<Country> retrieveAll() {
        return Collections.unmodifiableList(countries);
    }

    @Override
    public void add(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Country should not be null");
        }
        if (countries.contains(country)) {
            throw new IllegalArgumentException("Country name should be unique");
        }
        countries.add(country);
    }

    @Override
    public void update(String oldCountryName, String newCountryName) {
        int position = countries.indexOf(new Country(oldCountryName));
        if (position == -1) {
            throw new IllegalArgumentException("Country to update does not exist");
        }
        countries.get(position).setName(newCountryName);
    }

    @Override
    public void remove(String countryNameToDelete) {
        int position = countries.indexOf(new Country(countryNameToDelete));
        if (position == -1) {
            throw new IllegalArgumentException("Country to delete does not exist");
        }
        countries.remove(position);
    }
}
