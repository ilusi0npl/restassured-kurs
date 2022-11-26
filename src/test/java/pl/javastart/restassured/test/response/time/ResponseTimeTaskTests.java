package pl.javastart.restassured.test.response.time;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.testng.Assert.assertTrue;

public class ResponseTimeTaskTests {

    @BeforeClass
    public void beforeMethod() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectResponseTime(Matchers.lessThan(1000L), TimeUnit.MILLISECONDS);
        ResponseSpecification responseSpecification = responseSpecBuilder.build();

        RestAssured.responseSpecification = responseSpecification;
    }

    @Test
    public void givenExistingPetWithStatusSoldWhenGetPetWithSoldStatusThenPetWithStatusIsReturnedTest() {
        Category category = new Category();
        category.setId(777);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        Pet pet = new Pet();
        pet.setId(123);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("sold");

        given().body(pet).contentType("application/json")
                .when().post("pet")
                .then().statusCode(200);

        Pet[] pets = given().body(pet).contentType("application/json")
                .queryParam("status", "sold")
                .when().get("pet/findByStatus")
                .then().statusCode(200).extract().as(Pet[].class);

        assertTrue(Arrays.asList(pets).size() > 0, "List of pets");
    }

}
