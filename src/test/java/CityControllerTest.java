import com.dhex.shipping.controller.CityController;
import com.dhex.shipping.model.ActivityIndicatorEnum;
import com.dhex.shipping.model.City;
import com.dhex.shipping.services.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.when;

/**
 * Created by Juan Pablo on 23/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {

    CityController cityController;

    @Mock
    CityService cityService;

    @Before
    public void setUp() {
        cityController = new CityController(cityService);
    }

    @Test
    public void shouldCreateACityWhenNameAndCountryCodeAreComplete() throws URISyntaxException {
        String expectedCityName = "Lima";
        long expectedCountryCode = 1L;
        when(cityService.create(expectedCityName, expectedCountryCode))
                .thenReturn(new City(1L, expectedCityName, true, expectedCountryCode));
        ResponseEntity<City> cityResponseEntity = cityController.create(new City(expectedCityName, expectedCountryCode));
        assertThat(cityResponseEntity.getStatusCode(), is(HttpStatus.OK));
        City city = cityResponseEntity.getBody();
        assertThat(city.getId(), is(not(0)));
        assertThat(city.getName(), is(expectedCityName));
        assertThat(city.isEnabled(), is(true));
        assertThat(city.getCountryCode(), is(expectedCountryCode));
    }

    @Test
    public void shouldUpdateACity() {
        String expectedCityName = "Lima";
        long expectedCountryCode = 1L;
        long expectedCityCode = 1L;
        boolean expectedEnabled = false;
        when(cityService.update(expectedCityCode, expectedEnabled))
                .thenReturn(new City(expectedCityCode, expectedCityName, expectedEnabled, expectedCountryCode));
        City city = cityController.update(expectedCityCode, expectedEnabled);
        assertThat(city.getId(), is(not(0)));
        assertThat(city.getName(), is(expectedCityName));
        assertThat(city.isEnabled(), is(expectedEnabled));
        assertThat(city.getCountryCode(), is(expectedCountryCode));
    }

    @Test
    public void shouldSearchCitiesByCountryCode(){
        long expectedCountryCode = 1L;
        List<City> expectedList = new ArrayList<>();
        City city = new City(1L, "Lima", true, expectedCountryCode);
        expectedList.add(city);
        when(cityService.search(expectedCountryCode, ActivityIndicatorEnum.ENABLE)).thenReturn(expectedList);

        List<City> list = cityController.search(expectedCountryCode, ActivityIndicatorEnum.ENABLE);
        assertThat(list.size(), is(1));
    }

}
