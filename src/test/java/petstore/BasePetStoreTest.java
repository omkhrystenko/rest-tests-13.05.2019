package petstore;

import io.restassured.RestAssured;
import org.junit.Before;

public class BasePetStoreTest {

    @Before
    public void before(){
        RestAssured.baseURI = Config.BASE_URI;
    }


}
