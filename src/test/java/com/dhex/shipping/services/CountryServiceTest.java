package com.dhex.shipping.services;

import com.dhex.shipping.dao.CountryDao;
import com.dhex.shipping.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    private CountryService countryService;
    @Mock
    private CountryDao countryDao;

    @Before
    public void setUp() {
        countryService =new CountryService(countryDao);
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
        assertEquals(country.getId(), expectedId);
        assertThat(country.getName(), is(countryName));
    }

}
