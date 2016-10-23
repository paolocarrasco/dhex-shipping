import com.dhex.shipping.controller.CityController;
import com.dhex.shipping.services.CityService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

}
