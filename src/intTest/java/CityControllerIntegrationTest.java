import com.dhex.shipping.Main;
import com.dhex.shipping.model.ActivityIndicatorEnum;
import com.dhex.shipping.model.City;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

/**
 * Created by Juan Pablo on 30/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturnResponseCode200WhenCreating() throws Exception {
        createCity("Lima", 1L)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("name", is("Lima"));
    }

    @Test
    public void shouldReturnResponseCode400WhenCreatingAndNameExistsForCountryCode() throws Exception {
        createCity("Trujillo", 1L);
        createCity("Trujillo", 1L)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .body(is("Non existing country ID"));
    }

    @Test
    public void shouldReturnResponseCode200WhenUpdating() throws Exception {
        City createdCity = createCity("Cuzco", 1L).getBody().as(City.class);
        updateCity(createdCity.getId(), false)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("name", is("Cuzco"))
                .and()
                .body("enabled", is(false));
    }

    @Test
    public void shouldReturnResponseCode400WhenUpdatingWhenIdDoesNotExist() throws Exception {
        updateCity(0L, false)
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnResponseCode200WhenSearchingByCountryCode() {
        long countryCode = 2L;
        createCity("Cuzco", countryCode);
        createCity("Arequipa", countryCode);

        searchCity(countryCode, ActivityIndicatorEnum.ENABLE.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("size()", is(2));
    }

    @Test
    public void shouldReturnResponseCode200WhenSearchingByCountryCodeAndEnabledStatus() {
        long countryCode = 3L;
        createCity("Cuzco", countryCode);
        createCity("Arequipa", countryCode);
        createCity("Iquitos", countryCode);
        City createdCity = createCity("Lima", countryCode).getBody().as(City.class);
        updateCity(createdCity.getId(), false);

        searchCity(countryCode, ActivityIndicatorEnum.ENABLE.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("size()", is(3));
    }

    @Test
    public void shouldReturnResponseCode200WhenSearchingByCountryCodeAndReceivingAllCode() {
        long countryCode = 4L;
        createCity("Cuzco", countryCode);
        createCity("Arequipa", countryCode);
        createCity("Iquitos", countryCode);
        City createdCity = createCity("Lima", countryCode).getBody().as(City.class);
        updateCity(createdCity.getId(), false);

        searchCity(countryCode, ActivityIndicatorEnum.ALL.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("size()", is(4));
    }

    private Response createCity(String cityName, Long countryCode) {
        return given()
                .contentType(ContentType.JSON)
                .body(new City(cityName, countryCode))
                .when()
                .post("/cities");
    }

    private Response updateCity(Long cityCode, boolean newEnabled) {
        City city = new City();
        city.setEnabled(newEnabled);
        return given()
                .contentType(ContentType.JSON)
                .body(city)
                .when()
                .put("/cities/" + cityCode);
    }

    private Response searchCity(Long countryCode, Long activityIndicatorId) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/cities" +
                        "/" + countryCode +
                        "/" + activityIndicatorId);
    }

}
