package com.dhex.shipping.services;

import com.dhex.shipping.model.Country;

import java.util.List;

public interface CountryService {
    List<Country> retrieveAll();

    void add(Country country);

    void update(String oldCountryName, String newCountryName);

    void remove(String countryNameToDelete);
}
