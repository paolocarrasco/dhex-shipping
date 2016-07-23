package com.dhex.shipping.builders;

import com.dhex.shipping.model.Country;

public class CountryBuilder {
    private String name = "Peru";

    public CountryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Country create() {
        return new Country(name);
    }
}