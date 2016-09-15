package com.dhex.shipping.dao;

import com.dhex.shipping.model.Country;

import java.util.List;

public interface CountryDao {
    Country insert(String countryName);

    List<Country> listAll();
}
