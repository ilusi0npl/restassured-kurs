package pl.javastart.restassured.test.response.time;

import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class ResponseTimeTests {

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedInSpecifiedTimeTest() {

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

        long responseTime = given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200)
                .extract().timeIn(TimeUnit.SECONDS);

        System.out.println("Response time is " + responseTime);

        assertTrue(responseTime <= 1, "Response time");
    }

}
