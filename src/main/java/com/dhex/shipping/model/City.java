package com.dhex.shipping.model;

/**
 * Created by Juan Pablo on 23/10/2016.
 */
public class City {
    private long id;
    private String name;
    private boolean enabled;
    private long countryCode;

    public City(long id, String name, boolean enabled, long countryCode) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.countryCode = countryCode;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public long getCountryCode() {
        return countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return !(name != null ? !name.equals(city.name) : city.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
