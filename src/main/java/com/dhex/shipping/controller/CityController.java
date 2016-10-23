package com.dhex.shipping.controller;

import com.dhex.shipping.services.CityService;

/**
 * Created by Juan Pablo on 23/10/2016.
 */
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }
}
