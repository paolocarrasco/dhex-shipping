package com.dhex.shipping.model;

import com.dhex.shipping.builders.CountryBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountryTest {

    @Test
    public void shouldReturnTrueIfTwoCountriesHaveSameName() {
        // act
        Country originalCuba = CountryBuilder.create().with(c -> c.name = "Cuba").now();
        Country cubaWithDifferentId = CountryBuilder.create().with(c -> c.name = "Cuba").with(c -> c.id = 2L).now();
        boolean comparison = originalCuba.equals(cubaWithDifferentId);

        // assert
        assertThat(comparison, is(true));
    }

    @Test
    public void shouldReturnFalseIfTwoCountriesHaveDifferentNames() {
        // act
        Country elSalvador = CountryBuilder.create().with(c -> c.name = "El Salvador").now();
        Country mexico = CountryBuilder.create().with(c -> c.name = "Mexico").with(c -> c.id = 2L).now();
        boolean comparison = elSalvador.equals(mexico);

        // assert
        assertThat(comparison, is(false));
    }
}