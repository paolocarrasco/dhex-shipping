package com.dhex.shipping.model;

public class Country implements Comparable<Country> {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Country country = (Country) o;

        return name != null ? name.equals(country.name) : country.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int compareTo(Country anotherCountry) {
        if (anotherCountry == null) {
            return 1;
        }
        return getName().compareToIgnoreCase(anotherCountry.getName());
    }
}
