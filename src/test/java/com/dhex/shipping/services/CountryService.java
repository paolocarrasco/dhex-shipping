package com.dhex.shipping.services;

import com.dhex.shipping.dao.CountryDao;
import com.dhex.shipping.model.Country;

public class CountryService {
    private CountryDao countryDao;

    public CountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public Country create(String name) {
        return countryDao.insert(name);
    }
}
