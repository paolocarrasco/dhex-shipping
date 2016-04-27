package com.dhex.shipping.services;

import com.dhex.shipping.builders.CountryBuilder;
import com.dhex.shipping.model.Country;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CountryServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private CountryService countryService;

    @Before
    public void setUp() {
        countryService = CountryServiceFactory.create();
    }

    @Test
    public void testRetrieveAllDoesntRetrieveAnythingIfThereAreNoCountries() {
        List<Country> countries = countryService.retrieveAll();

        assertEquals(0, countries.size());
    }

    @Test
    public void testRetrieveAllRetrievesOneCountryAfterAdding() {
        countryService.add(new CountryBuilder().create());
        List<Country> countries = countryService.retrieveAll();

        assertEquals(1, countries.size());
        assertEquals("Peru", countries.get(0).getName());
    }

    @Test
    public void testRetrieveReturnsTheCountryWhenExists() {
        countryService.add(new CountryBuilder().create());
        Country retrievedCountry = countryService.retrieve("Peru");
        assertEquals("Peru", retrievedCountry.getName());
    }

    @Test
    public void testAddThrowsAnErrorIfNullIsSent() {
        expectedException.expectMessage("Country should not be null");
        countryService.add(null);
    }

    @Test
    public void testAddThrowsAnErrorIfCountryNameIsDuplicated() {
        countryService.add(new CountryBuilder().withName("Ecuador").create());
        expectedException.expectMessage("Country name should be unique");

        countryService.add(new CountryBuilder().withName("Ecuador").create());
    }

    @Test
    public void testUpdateReplacesTheName() {
        String originalCountryName = "Brasil";
        Country countryToUpdate = new CountryBuilder().withName(originalCountryName).create();
        countryService.add(countryToUpdate);
        String newCountryName = "Brazil";

        countryService.update(originalCountryName, newCountryName);
        List<Country> countries = countryService.retrieveAll();

        assertEquals(newCountryName, countries.get(0).getName());
    }

    @Test
    public void testUpdateDoesntAddOrRemoveAnyCountry() {
        String originalCountryName = "Brasil";
        String newCountryName = "Brazil";

        Country countryToUpdate = new CountryBuilder().withName(originalCountryName).create();
        countryService.add(countryToUpdate);
        countryService.update(originalCountryName, newCountryName);

        List<Country> countries = countryService.retrieveAll();

        assertEquals(1, countries.size());
    }

    @Test
    public void testUpdateShouldThrowAnErrorIfOriginalCountryNameDoesNotExist() {
        expectedException.expectMessage("Country to update does not exist");
        countryService.update("nowhere", "myplace");
    }

    @Test
    public void testDeleteShouldRemoveTheCountryByName() {
        String countryName = "Argentina";
        Country countryToDelete = new CountryBuilder().withName(countryName).create();
        countryService.add(new CountryBuilder().withName("Uruguay").create());
        countryService.add(new CountryBuilder().withName("Paraguay").create());
        countryService.add(countryToDelete);

        countryService.remove(countryName);
        List<Country> countries = countryService.retrieveAll();

        assertFalse(countries.contains(countryToDelete));
        assertEquals(2, countries.size());
    }

    @Test
    public void testDeleteShouldThrowAnErrorIfCountryToRemoveDoesNotExist() {
        expectedException.expectMessage("Country to delete does not exist");

        Country countryToUpdate = new CountryBuilder().withName("Venezuela").create();
        countryService.add(countryToUpdate);

        countryService.remove("Mexico");
    }
}
