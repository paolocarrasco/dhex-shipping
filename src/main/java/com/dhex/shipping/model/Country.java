package com.dhex.shipping.model;

/**
 * Represents a country in the world.
 */
public class Country {
    private long id;
    private String name;
    private boolean enabled;

    public Country(long id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
