package com.dhex.shipping.controller;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.exceptions.NotExistingCityException;
import com.dhex.shipping.model.ActivityIndicatorEnum;
import com.dhex.shipping.model.City;
import com.dhex.shipping.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by Juan Pablo on 23/10/2016.
 */
@RestController
@CrossOrigin(allowCredentials = "true", value = "*", methods = {GET, POST, PUT}, allowedHeaders = "*")
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<City> create(@RequestBody City city) throws URISyntaxException {
        City createdCity = cityService.create(city.getName(), city.getCountryCode());
        return ResponseEntity.ok(createdCity);
    }

    @RequestMapping(method = PUT, value = "/{cityCode}")
    public City update(@PathVariable Long cityCode,
                       @RequestBody City city) {
        return cityService.update(cityCode, city.isEnabled());
    }

    @RequestMapping(method = GET, value = "/{countryCode}/{activityIndicatorId}")
         public List<City> search(@PathVariable Long countryCode,
                                  @PathVariable Long activityIndicatorId) {
        return cityService.search(countryCode
                , ActivityIndicatorEnum.getActivityIndicatorEnumById(activityIndicatorId));
    }

    @ExceptionHandler(value = DuplicatedEntityException.class)
    public ResponseEntity handle(DuplicatedEntityException ex) {
        return ResponseEntity.badRequest().body("Non existing country ID");
    }

    @ExceptionHandler(value = NotExistingCityException.class)
    public ResponseEntity handle(NotExistingCityException ex) {
        return ResponseEntity.badRequest().body("");
    }

    public List<City> search(long countryCode, ActivityIndicatorEnum status) {
        return cityService.search(countryCode, status);
    }


}
