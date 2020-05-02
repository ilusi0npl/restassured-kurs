package pl.javastart.restassured.test.assertions;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredAssertionsTests {

    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {
        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        Pet pet = new Pet();
        pet.setId(123);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        given().body(pet).contentType("application/json")
                .when().post("pet")
                .then().statusCode(200);

        given().pathParam("petId", pet.getId())
                .when().get("pet/{petId}")
                .then()
                .assertThat().statusCode(200)
                .assertThat().contentType("application/json")
                .assertThat().body("id", equalTo(123),
                "category.id", equalTo(1),
                "category.name", equalTo("dogs"),
                "photoUrls[0]", equalTo("http://photos.com/dog1.jpg"),
                "tags[0].id", equalTo(1),
                "tags[0].name", equalTo("dogs-category"),
                "status", equalTo("available"));
    }

}
