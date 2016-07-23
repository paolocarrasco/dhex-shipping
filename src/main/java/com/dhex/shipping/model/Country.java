package com.dhex.shipping.model;

public class Country {

    private String name;

    public Country(String countryName) {
        this.name = countryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
