import com.dhex.shipping.Application;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void preparePort() {
        RestAssured.port = port;
    }

    @Test
    public void name() {
        when()
                .get("/")
        .then()
                .assertThat()
                .statusCode(is(HttpStatus.OK.value()));
    }
}
