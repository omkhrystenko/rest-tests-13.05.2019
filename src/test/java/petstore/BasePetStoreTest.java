package petstore;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import petstore.models.CategoryModel;
import petstore.models.PetModel;
import petstore.models.TagModel;
import petstore.petFactory.PetsData;

import static org.hamcrest.core.IsEqual.equalTo;

public class BasePetStoreTest {

    {
        RestAssured.baseURI = Config.BASE_URI;
    }

    @Before
    public void before(){
        PetsData.setPetBobcat();
        PetModel petModel = new PetModel(PetsData.petId, new CategoryModel(), PetsData.petName,
                new String[]{PetsData.hostName}, new TagModel[]{new TagModel()},
                "AVAILABLE");

        ValidatableResponse response = RestAssured.given()
                .log().uri()
                .contentType("application/json")
                .body(petModel)
                .post(Config.CREATE_UPDATE_PET)
                .then()
                .log().all()
                .statusCode(200);

        RestAssured.given()
                //.log().uri()
                .get(Config.GET_PET_BY_ID, PetsData.petId)//параметризированый запрос формирования BaseURL
                .then()
                .log().all()
                .statusCode(200)
                .assertThat().body("name", equalTo(PetsData.petName));
    }


    @After
    public void after(){
        ValidatableResponse response = RestAssured.given()
                //.log().uri()
                .delete(Config.GET_PET_BY_ID, PetsData.petId)
                .then()
                .log().all()
                .statusCode(200);

        RestAssured.given()
                //.log().uri()
                .get(Config.GET_PET_BY_ID, PetsData.petId)//параметризированый запрос формирования BaseURL
                .then()
                .log().all()
                .statusCode(404);
    }
}
