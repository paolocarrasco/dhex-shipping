package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.model.City;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Juan Pablo on 23/10/2016.
 */
public class CityService {
    private static Long sequence = 0L;
    Map<String, Set<Long>> citiesMap = new HashMap<>();
    public City create(String cityName, Long countryCode) {
        if (cityName == null || countryCode == null || cityName.length() > 100) {
            throw new InvalidArgumentDhexException();
        }

        Set<Long> countriesSetForCityName = citiesMap.get(cityName);
        if (countriesSetForCityName == null) {
            countriesSetForCityName = new HashSet<>();
        }

        if (countriesSetForCityName.contains(countryCode)) {
            // TODO crear excepcion personalizada
            throw new InvalidArgumentDhexException();
        }

        countriesSetForCityName.add(countryCode);
        citiesMap.put(cityName, countriesSetForCityName);

        return new City(++sequence, cityName, true, countryCode);
    }
}
