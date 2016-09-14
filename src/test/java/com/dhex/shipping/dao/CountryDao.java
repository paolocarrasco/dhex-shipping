package com.dhex.shipping.dao;

import com.dhex.shipping.model.Country;

public interface CountryDao {
    Country insert(String countryName);
}
