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
}
