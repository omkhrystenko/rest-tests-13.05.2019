import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import jdk.nashorn.internal.parser.JSONParser;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Concurrent;
import org.junit.Test;
import io.restassured.http.ContentType;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.*;

@Concurrent //позволяет запускать в многопоточности
@RunWith(SerenityRunner.class)
public class WeatherTest {

    @Test
    public void getWeatherPerCityTest(){
        RestAssured.baseURI = "https://pinformer.sinoptik.ua/";

        //lang=ua&return_id=1&q=Lviv
        String cityName = "Kharkiv";

        ValidatableResponse response = SerenityRest.given()//SerenityRest отвечает за попадание в отчет request (запросов)
                .param("lang", "ua")
                .param("return_id", 1)
                .param("q", cityName)
                .log()
                .uri()
                .get("search.php")
                .then()//Все что стоит после метода .then() относится к assert. Используется паттерн girkin language - when - then
                .log().all()
                .statusCode(200);

        String cityLine = response.extract().asString();

        int borderCityId = cityLine.lastIndexOf("|");
        String cityId = cityLine.substring(borderCityId + 1);
        System.out.println(cityId);

        //Getting wheather in Lviv by Id

        //RestAssured.basePath = "pinformer4.php";
        response = SerenityRest.given()
                .param("type", "js")
                .param("lang", "ua")
                .param("id", cityId)
                .log()
                .uri()
                .get("pinformer4.php")
                .then()
                .log().all()
                .statusCode(200)
                //.body("any {it.key == '{pcity}',", is(true));//используем Groovy path with hamcrest matchers
                                                    //проверяем что в body присутствует ключ {pcity}
                .body("'{pcity}'", is(cityId))// используем JsonPath with hamcrest matchers
                .body("'{pcity}'", is(not(1)));// используем JsonPath with hamcrest matchers
        //Для сравнивания целого JSON используются JSON схемы, описующие наборы ключей и типов данных,
        //которые им соответствуют
        //System.out.println(response.extract().path("'{pcity}'"));
        System.out.println(response.extract().response().jsonPath().getString("$"));
        System.out.println(response.extract().response().jsonPath().getString("'{temp}'"));


    }

    //JsonPath - для парсинга JSON
    //Groovy path - для парсинга JSON

}
