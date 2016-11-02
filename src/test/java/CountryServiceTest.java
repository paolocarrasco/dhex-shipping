import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.model.Country;
import com.dhex.shipping.services.CountryService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by avantica on 18/10/2016.
 */
public class CountryServiceTest {

    CountryService countryService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        countryService = new CountryService();
    }

    @Test
    public void shouldCreateACountryWhenSavingAndNameIsComplete() throws Exception {
        String expectedCountry = "Argentina";
        Country country = countryService.create(expectedCountry);
        assertThat(country.getId(), is(not(0)));
        assertThat(country.getName(), is(expectedCountry));
        assertThat(country.isEnabled(), is(true));
    }

    @Test
    public void shouldThrowAnErrorWhenNameAlreadyExists() {
        expectedException.expect(DuplicatedEntityException.class);
        String expectedCountry = "Peru";
        countryService.create(expectedCountry);
        countryService.create(expectedCountry);
    }
}
