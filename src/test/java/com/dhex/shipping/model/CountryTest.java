package com.dhex.shipping.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountryTest {

    @Test
    public void shouldReturnTrueIfTwoCountriesHaveSameName() {
        // act
        boolean comparison = new Country(2, "Cuba", false).equals(new Country(1, "Cuba", true));

        // assert
        assertThat(comparison, is(true));
    }

    @Test
    public void shouldReturnFalseIfTwoCountriesHaveDifferentNames() {
        // act
        boolean comparison = new Country(3, "Brazil", false).equals(new Country(3, "India", false));

        // assert
        assertThat(comparison, is(false));
    }
}