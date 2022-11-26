package pl.javastart.restassured.test.jsonpath;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class JsonPathTests {

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {

        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        Pet pet = new Pet();
        pet.setName("Burek");
        pet.setId(123);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);

        JsonPath jsonPathResponse = given().log().method().log().uri()
                .pathParam("petId", pet.getId())
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
                .then().log().all().statusCode(200)
                .extract().jsonPath();

        String petName = jsonPathResponse.getString("name");
        String actualCategoryName = jsonPathResponse.getString("category.name");
        String tagName = jsonPathResponse.getString("tags[0].name");

        assertEquals(petName, pet.getName(), "Pet name");
        assertEquals(actualCategoryName, pet.getCategory().getName(), "Pet category name");
        assertEquals(tagName, pet.getTags().get(0).getName(), "Pet Tag name");
    }

}
