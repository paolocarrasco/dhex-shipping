import com.dhex.shipping.controller.CountryController;
import com.dhex.shipping.model.Country;
import com.dhex.shipping.services.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by avantica on 18/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    CountryController countryController;

    @Mock
    CountryService countryService;

    @Before
    public void setUp() throws Exception {
        countryController = new CountryController(countryService);
    }

    @Test
    public void shouldCreateACountryWhenSavingAndNameIsComplete(){
        when(countryService.create("Argentina")).thenReturn(new Country(1L, "Argentina", true));
        String expectedCountry = "Argentina";
        Country country = countryController.save(expectedCountry);
        assertThat(country.getId(), is(not(0)));
        assertThat(country.getName(), is(expectedCountry));
        assertThat(country.isEnabled(), is(true));
    }

    @Test
    public void shouldCreateCountriesWithDifferentIdsWhenSaving(){
        when(countryService.create("Peru")).thenReturn(new Country(1L, "Peru", true));
        when(countryService.create("Argentina")).thenReturn(new Country(2L, "Argentina", true));
        Country country = countryController.save("Peru");
        Country countryDos = countryController.save("Argentina");
        assertThat(country.getId(), is(not(countryDos.getId())));
        assertThat(country.getName(), is(not(countryDos.getName())));
        assertThat(country.isEnabled(), is(countryDos.isEnabled()));
    }
}
