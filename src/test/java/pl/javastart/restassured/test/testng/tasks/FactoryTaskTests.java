package pl.javastart.restassured.test.testng.tasks;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class FactoryTaskTests {

    private String petName;
    private String petStatus;

    public FactoryTaskTests(String petName, String petStatus) {
        this.petName = petName;
        this.petStatus = petStatus;
    }

    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType("application/json").build();
    }

    @Test
    public void givenPetStatusWhenPetIsCreatedThenPetWithPetStatusIsAvailableTest() {

        Category category = new Category();
        category.setId(1);
        category.setName("dogs");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("dogs-category");

        Pet pet = new Pet();
        pet.setName(petName);
        pet.setId(46465);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("http://photos.com/dog1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus(petStatus);

        Pet actualPet = given().body(pet)
                .when().post("pet")
                .then().statusCode(200).extract().as(Pet.class);

        assertEquals(actualPet.getStatus(), pet.getStatus(), "Pet status is not as expected");
        assertEquals(actualPet.getName(), pet.getName(), "Pet name is not as expected");
    }

    @Factory
    public static Object[] factoryMethod() {
        FactoryTaskTests firstTestToExecute = new FactoryTaskTests("Burek", "available");
        FactoryTaskTests secondTestToExecute = new FactoryTaskTests("Reksio", "pending");
        FactoryTaskTests thirdTestToExecute = new FactoryTaskTests("Ponzi", "sold");
        return new Object[]{
                firstTestToExecute,
                secondTestToExecute,
                thirdTestToExecute};
    }

}
