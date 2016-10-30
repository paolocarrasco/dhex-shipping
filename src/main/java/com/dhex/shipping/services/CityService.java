package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.exceptions.NotExistingCityException;
import com.dhex.shipping.model.ActivityIndicatorEnum;
import com.dhex.shipping.model.City;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Juan Pablo on 23/10/2016.
 */

@Component
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
            throw new DuplicatedEntityException();
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

    public List<City> search(long countryCode, ActivityIndicatorEnum status) {
        // ALL IS REPRESENTED BY -1
        if (countryCode == -1) {
            return new ArrayList<>(citiesByIdMap.values());
        }
        Set<City> set = citiesByCountryMap.get(countryCode);
        if (set != null) {
            return getFinalList(set, status);
        }
        return Collections.emptyList();
    }

    private List<City> getFinalList(Set<City> set, ActivityIndicatorEnum status) {
        List<City> finalList;
        Long idActivityIndicator = status.getId();
        if (!ActivityIndicatorEnum.ALL.equals(status)) {
            finalList = filterCities(set, idActivityIndicator);
        } else {
            finalList = new ArrayList<>(set);
        }
        return finalList;
    }

    private List<City> filterCities(Set<City> set, Long idActivityIndicator) {
        Iterator<City> iter = set.iterator();
        while (iter.hasNext()) {
            City city = iter.next();
            if (!idActivityIndicator.equals(ActivityIndicatorEnum.getIdByBooleanStatus(city.isEnabled()))) {
                iter.remove();
            }
        }
        return new ArrayList<>(set);
    }
}
