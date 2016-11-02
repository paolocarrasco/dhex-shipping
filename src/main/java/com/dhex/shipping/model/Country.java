package com.dhex.shipping.model;

/**
 * Represents a country in the world.
 */
public class Country {
    private long id;
    private String name;
    private boolean enabled;

    public Country(long id, String name, boolean enabled){
        this.id = id;
        this.name = name;
        this.enabled = enabled;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (!name.equals(country.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
