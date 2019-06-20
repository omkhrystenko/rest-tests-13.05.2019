package petstore.tests;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.TestData;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import petstore.endpoints.PetEndpoint;
import petstore.models.petModelPack.CategoryModel;
import petstore.models.petModelPack.PetModel;
import petstore.models.petModelPack.TagModel;

import java.util.Arrays;
import java.util.Collection;

@Concurrent(threads = "4") //позволяет запускать в многопоточности
@RunWith(SerenityParameterizedRunner.class)
public class PetCreateCombinationsTest {

    @Steps
    PetEndpoint petEndpoint;


    @TestData
    public static Collection<Object[]> testData(){
        return Arrays.asList(new Object[][]{
                {"chupacabra", 200},
                {"horse", 200},
                {"HORSE", 200},
                {"34532", 200},
                {"ЛОШАДЬ", 200},
                {"df34Ава", 200},
                {"<script>alert(123)</script>", 200},
                {"‘ or ‘a’ = ‘a’; DROP TABLE user; SELECT * FROM blog WHERE code LIKE", 200},
                {"«♣☺♂»  «»‘~!@#$%^&*()?>./<][ /*<!—«» «${code}»;—>", 200}
        });
    }

    private final String  petName;
    private final int statusCode;

    public PetCreateCombinationsTest(String petName, int statusCode) {
        this.petName = petName;
        this.statusCode = statusCode;
    }

    @Test
    public void petCreateNameCombinationsTest(){


        PetModel petModel = new PetModel(
                new CategoryModel(),
                petName,
                new String[]{"www.zoo.com"},
                new TagModel[]{new TagModel()},
                "AVAILABLE");

        petEndpoint
                .createPet(petModel)
                .statusCode(statusCode);

    }

}
