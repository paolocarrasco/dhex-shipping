import com.dhex.shipping.exceptions.DuplicatedEntityException;
import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.exceptions.NotExistingCityException;
import com.dhex.shipping.model.City;
import com.dhex.shipping.services.CityService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.when;

/**
 * Created by Juan Pablo on 23/10/2016.
 */
public class CityServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    CityService cityService;

    @Before
    public void setUp() {
        cityService = new CityService();
    }

    // =============
    // Create tests
    // =============
    @Test
    public void shouldCreateACityWhenNameAndCountryCodeAreComplete() {
        String expectedCityName = "Lima";
        long expectedCountryCode = 1L;
        City city = cityService.create(expectedCityName, expectedCountryCode);
        assertThat(city.getId(), is(not(0)));
        assertThat(city.getName(), is(expectedCityName));
        assertThat(city.isEnabled(), is(true));
        assertThat(city.getCountryCode(), is(expectedCountryCode));
    }

    @Test
    public void shouldCreateCitiesWithDifferentIdsWhenSaving() {
        String expectedCityName1 = "Lima";
        String expectedCityName2 = "Arequipa";
        long expectedCountryCode = 1L;
        City city1 = cityService.create(expectedCityName1, expectedCountryCode);
        City city2 = cityService.create(expectedCityName2, expectedCountryCode);
        assertThat(city1.getId(), is(not(city2.getId())));
        assertThat(city1.getName(), is(not(city2.getName())));
        assertThat(city1.isEnabled(), is(city2.isEnabled()));
        assertThat(city1.getCountryCode(), is(city2.getCountryCode()));
    }

    @Test
    public void shouldThrowExceptionWhenCreateAndNameIsMissing() {
        expectedException.expect(InvalidArgumentDhexException.class);
        String expectedCityName = null;
        long expectedCountryCode = 1L;
        City city = cityService.create(expectedCityName, expectedCountryCode);
    }

    @Test
    public void shouldThrowExceptionWhenCreateAndCountryCodeIsMissing() {
        expectedException.expect(InvalidArgumentDhexException.class);
        String expectedCityName = "Lima";
        Long expectedCountryCode = null;
        City city = cityService.create(expectedCityName, expectedCountryCode);
    }

    @Test
    public void shouldThrowExceptionWhenCreateAndNameLengthIsInvalid() {
        expectedException.expect(InvalidArgumentDhexException.class);
        String expectedCityName = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean ma";
        long expectedCountryCode = 1L;
        City city = cityService.create(expectedCityName, expectedCountryCode);
    }

    @Test
    public void shouldThrowExceptionWhenCreateAndNameExistsForCountryCode() {
        expectedException.expect(DuplicatedEntityException.class);
        String expectedCityName = "Lima";
        long expectedCountryCode = 1L;
        City city1 = cityService.create(expectedCityName, expectedCountryCode);
        City city2 = cityService.create(expectedCityName, expectedCountryCode);
    }

    @Test
    public void shouldCreateCityWhenCreateAndNameExistsForDifferentCountryCode() {
        String expectedCityName = "Lima";
        long expectedCountryCode1 = 1L;
        long expectedCountryCode2 = 2L;
        City city1 = cityService.create(expectedCityName, expectedCountryCode1);
        City city2 = cityService.create(expectedCityName, expectedCountryCode2);
        assertThat(city1.getId(), is(not(city2.getId())));
        assertThat(city1.getName(), is(city2.getName()));
        assertThat(city1.isEnabled(), is(city2.isEnabled()));
        assertThat(city1.getCountryCode(), is(not(city2.getCountryCode())));
    }

    // =============
    // Update tests
    // =============
    @Test
    public void shouldUpdateCityWhenIdExists() {
        String expectedCityName = "Lima";
        long expectedCountryCode = 1L;
        City city = cityService.create(expectedCityName, expectedCountryCode);

        long expectedCityCode = city.getId();
        boolean expectedNewEnabled = false;
        city = cityService.update(expectedCityCode, expectedNewEnabled);

        assertThat(city.getId(), is(expectedCityCode));
        assertThat(city.getName(), is(expectedCityName));
        assertThat(city.isEnabled(), is(expectedNewEnabled));
        assertThat(city.getCountryCode(), is(expectedCountryCode));
    }

    @Test
    public void shouldThrowExceptionWhenUpdateCityWhenIdDoesNotExist() {
        expectedException.expect(NotExistingCityException.class);
        long expectedCityCode = 10L;
        boolean expectedNewEnabled = false;
        cityService.update(expectedCityCode, expectedNewEnabled);
    }

    // =============
    // Search tests
    // =============


}
