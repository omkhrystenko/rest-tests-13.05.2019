package petstore.tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import petstore.endpoints.Config;
import petstore.endpoints.PetEndpoint;
import petstore.models.CategoryModel;
import petstore.models.PetModel;
import petstore.models.TagModel;

import static petstore.endpoints.PetEndpoint.*;

public class PetStoreTest {

  private PetEndpoint petEndpoint = new PetEndpoint();

  @Test
    public void getPetByIdTest(){
        int petId = 2;
        petEndpoint
                .getPetById(petId)
                .statusCode(200);
    }

    @Test
    public void getPetByStatus(){

      for(Status status : Status.values()) {
          petEndpoint
                  .getPetByStatus(status)
                  .statusCode(200);
      }
    }


    @Test
    public void createPetTest(){
        PetModel petModel = new PetModel(
                7,
                new CategoryModel(),
                "Tiger",
                new String[]{"www.zoo.com"},
                new TagModel[]{new TagModel()},
                "AVAILABLE");

            petEndpoint
                    .createPet(petModel)
                    .statusCode(200);
    }

    @Test
    public void deleteCreatedPet(){

      int petId =  7;
            petEndpoint
                    .deletePet(petId)
                    .statusCode(200);

    }

}
