package pl.javastart.restassured.test.testng.tasks;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class DataProviderTaskTests {

    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType("application/json").build();
    }

    @Test(dataProvider = "petStatus")
    public void givenPetStatusWhenPetIsCreatedThenPetWithPetStatusIsAvailableTest(String petStatus) {

        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        Pet pet = new Pet();
        pet.setName("Burek");
        pet.setId(46465);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus(petStatus);

        String actualPetStatus = given().body(pet)
                .when().post("pet")
                .then().statusCode(200).extract().jsonPath().getString("status");

        assertEquals(actualPetStatus, pet.getStatus(), "Pet status is not as expected");
    }

    @DataProvider(name = "petStatus")
    public Object[][] petStatus() {
        return new Object[][]{
                {"available"},
                {"pending"},
                {"sold"},
        };
    }
}
