package com.dhex.shipping.services;

import com.dhex.shipping.dao.CountryDao;
import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.model.Country;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    private CountryService countryService;
    @Mock
    private CountryDao countryDao;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        countryService = new CountryService(countryDao);
    }

    @Test
    public void shouldGenerateACountryAfterCreate() {
        // arrange
        String countryName = "Peru";
        long expectedId = 1000000000L;
        Country createdOnDaoCountry = new Country(expectedId, countryName, true);
        when(countryDao.insert(countryName))
                .thenReturn(createdOnDaoCountry);

        // act
        Country country = countryService.create(countryName);

        // assert
        assertThat(country.isEnabled(), is(true));
        assertThat(country.getId(), is(expectedId));
        assertThat(country.getName(), is(countryName));
    }

    @Test
    public void shouldHaveNameAsNotNullWhenCreateCountry() {
        // assert
        expectedException.expect(InvalidArgumentDhexException.class);
        expectedException.expectMessage("Name of the country should not be empty");

        // act
        countryService.create(null);
    }

    @Test
    public void shouldHaveNameAsNotEmptyWhenCreateCountry() {
        // assert
        expectedException.expect(InvalidArgumentDhexException.class);
        expectedException.expectMessage("Name of the country should not be empty");

        // act
        countryService.create(" ");
    }

    @Test
    public void shouldThrowAnExceptionIfNameIsLarge() {
        // assert
        expectedException.expect(InvalidArgumentDhexException.class);
        expectedException.expectMessage("Name of the country should not be greater than 100 chars");

        // act
        String largeCountryName = "A really really long country name in a far far far far far far far far far far universe from a really different dimension of the future past";
        countryService.create(largeCountryName);
    }
}
