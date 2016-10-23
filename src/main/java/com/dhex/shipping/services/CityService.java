package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.exceptions.NotExistingCityException;
import com.dhex.shipping.model.City;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Juan Pablo on 23/10/2016.
 */
public class CityService {
    private Long sequence = 0L;
    Map<Long, Set<City>> citiesByCountryMap = new HashMap<>();
    Map<Long, City> citiesByIdMap = new HashMap<>();
    public City create(String cityName, Long countryCode) {
        if (cityName == null || countryCode == null || cityName.length() > 100) {
            throw new InvalidArgumentDhexException();
        }

        Set<City> citiesSetForCountry = citiesByCountryMap.get(countryCode);
        if (citiesSetForCountry == null) {
            citiesSetForCountry = new HashSet<>();
        }

        City city = new City(++sequence, cityName, true, countryCode);

        if (!citiesSetForCountry.add(city)) {
            // TODO crear excepcion personalizada para nombre de ciudad existente
            throw new InvalidArgumentDhexException();
        }

        citiesByCountryMap.put(countryCode, citiesSetForCountry);
        citiesByIdMap.put(city.getId(), city);

        return city;
    }

    public City update(long cityCode, boolean newEnabled) {
        City city = citiesByIdMap.get(cityCode);
        if (city == null) {
            throw new NotExistingCityException();
        }
        city.setEnabled(newEnabled);
        return city;
    }
}
