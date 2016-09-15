package com.dhex.shipping.builders;

import com.dhex.shipping.model.Country;

import java.util.function.Consumer;

public final class CountryBuilder {
    public long id = 1L;
    public String name = "Peru";
    public boolean enabled = true;

    private CountryBuilder() {
        // to avoid unexpected instantiations
    }

    public static CountryBuilder create() {
        return new CountryBuilder();
    }

    public CountryBuilder with(Consumer<CountryBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public Country now() {
        return new Country(id, name, enabled);
    }
}