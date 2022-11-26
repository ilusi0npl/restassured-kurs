package pl.javastart.restassured.test.testng;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class FactoryTests {

    private String petId;
    private String expectedMessage;

    public FactoryTests(String petId, String expectedMessage) {
        this.petId = petId;
        this.expectedMessage = expectedMessage;
    }

    @BeforeMethod
    public void beforeMethod() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void givenNonExistingPetIdWhenGetPetThenPetNotFoundTest() {
        given()
                .when()
                .get("pet/{param}", petId)
                .then()
                .statusCode(404)
                .assertThat()
                .body("message", containsString(expectedMessage));
    }

    @Factory
    public static Object[] factoryMethod() {
        FactoryTests firstTestToExecute = new FactoryTests("0", "Pet not found");
        FactoryTests secondTestToExecute = new FactoryTests("aaa", "java.lang.NumberFormatException: For input string:");
        FactoryTests thirdTestToExecute = new FactoryTests("!!!", "java.lang.NumberFormatException: For input string:");
        return new Object[]{
                firstTestToExecute,
                secondTestToExecute,
                thirdTestToExecute};
    }

}
