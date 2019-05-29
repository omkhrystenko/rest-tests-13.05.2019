package petstore;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import petstore.models.CategoryModel;
import petstore.models.PetModel;
import petstore.models.TagModel;

public class PetStoreTest {

  static{
      //RestAssured.baseURI = "https://petstore.swagger.io/v2";//protocol + hostName + versionAPI(v2 - API for integration)
        RestAssured.baseURI = Config.BASE_URI;
  }
  private  enum Status {
      AVAILABLE,
      PENDING,
      SOLD
  }

  @Test
    public void getPetByIdTest(){
        int petId = 2;

        ValidatableResponse response = RestAssured.given()
                //.log().uri()
                .get(Config.GET_PET_BY_ID, petId)//параметризированый запрос формирования BaseURL
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void getPetByStatus(){


      for(Status status : Status.values()) {
          ValidatableResponse response = RestAssured.given()
                  //.log().uri()
                  .param("status", status)
                  .get(Config.GET_PET_BY_STATUS)
                  .then()
                  .log().all()
                  .statusCode(200);
          System.out.println("++++++++++++++++++++++" + status + "++++++++++++++++++++++++++++++++++");

      }
    }


    @Test

    public void createPetTest(){
        PetModel petModel = new PetModel(7, new CategoryModel(), "Tiger",
                new String[]{"www.zoo.com"}, new TagModel[]{new TagModel()},
                "AVAILABLE");

        RestAssured.given()
                .log().uri()
                //.header("accept", "application/json")
                //.header("Content-Type", "application/json")
                .contentType("application/json")
                .body(petModel)
                .post(Config.CREATE_UPDATE_PET)
                .then()
                .log().all()
                .statusCode(200);

    }



    @Test
    public void deleteCreatedPet(){

      String petId =  createPet("7");

            ValidatableResponse response = RestAssured.given()
                    //.log().uri()
                    .delete(Config.GET_PET_BY_ID, petId)
                    .then()
                    .log().all()
                    .statusCode(200);


    }






    private String createPet(String petId) {
      String petJson = "{\n" +
              "  \"id\":" + petId + ",\n" +
              "  \"category\": {\n" +
              "    \"id\": " + petId + ",\n" +
              "    \"name\": \"string\"\n" +
              "  },\n" +
              "  \"name\": \"hedgehog\",\n" +
              "  \"photoUrls\": [\n" +
              "    \"string\"\n" +
              "  ],\n" +
              "  \"tags\": [\n" +
              "    {\n" +
              "      \"id\": " + petId + ",\n" +
              "      \"name\": \"string\"\n" +
              "    }\n" +
              "  ],\n" +
              "  \"status\": \"available\"\n" +
              "}";

      ValidatableResponse response = RestAssured.given()
                //.log().uri()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(petJson)
                .post(Config.CREATE_UPDATE_PET)
                .then()
                .log().all()
                .statusCode(200);
      return petId;
    }

}
