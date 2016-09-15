package com.dhex.shipping.services;

import com.dhex.shipping.dao.CountryDao;
import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountryService {
    private static final String EMPTY_NAME_ERROR_MESSAGE = "Name of the country should not be empty";
    private static final String LARGE_NAME_ERROR_MESSAGE = "Name of the country should not be greater than 100 chars";
    private CountryDao countryDao;

    @Autowired
    public CountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public Country create(String name) {
        validateName(name);
        return countryDao.insert(name);
    }

    private void validateName(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new InvalidArgumentDhexException(EMPTY_NAME_ERROR_MESSAGE);
        }
        if(name.length() > 100) {
            throw new InvalidArgumentDhexException(LARGE_NAME_ERROR_MESSAGE);
        }
    }

    public List<Country> list() {
        return countryDao.listAll();
    }
}
