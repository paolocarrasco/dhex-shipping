package com.dhex.shipping.controller;

import com.dhex.shipping.model.City;
import com.dhex.shipping.services.CityService;

/**
 * Created by Juan Pablo on 23/10/2016.
 */
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    public City create(String cityName, long countryCode) {
        return cityService.create(cityName, countryCode);
    }

    public City update(Long cityCode, boolean newEnabled) {
        return cityService.update(cityCode, newEnabled);
    }
}
