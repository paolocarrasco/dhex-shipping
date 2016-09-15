package com.dhex.shipping.dao;

import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.Country;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

public class CountryBasicDaoTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private CountryDao countryDao;

    @Before
    public void setUp() throws Exception {
        countryDao = new CountryBasicDao();
    }

    @Test
    public void shouldReturnCountryIfCreateIsSuccessful() {
        // act
        Country country = countryDao.insert("Ecuador");

        // assert
        assertThat(country.getId(), is(1L));
        assertThat(country.getName(), is("Ecuador"));
        assertThat(country.isEnabled(), is(true));
    }

    @Test
    public void shouldGenerateASequentialIdForEachNewCountry() {
        // act
        Country country1 = countryDao.insert("Paraguay");
        Country country2 = countryDao.insert("Uruguay");

        // assert
        assertThat(country1.getId() + 1, is(country2.getId()));
    }

    @Test
    public void shouldThrowAnExceptionOnCreatingWithDuplicatedName() {
        // assert
        expectedException.expect(DuplicatedEntityException.class);
        expectedException.expectMessage("Country name Ecuador already exists");

        // act
        countryDao.insert("Ecuador");
        countryDao.insert("Ecuador");
    }

    @Test
    public void shouldListAllTheCountriesInsertedWhenListing() {
        countryDao.insert("Guyana");
        countryDao.insert("Surinam");

        List<Country> countries = countryDao.listAll();

        assertThat(countries, hasItems(
                hasProperty("name", is("Guyana")),
                hasProperty("name", is("Surinam"))));
    }
}