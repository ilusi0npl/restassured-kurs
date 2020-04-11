package pl.javastart.restassured.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SecondRestAssuredAutomatedTest {

    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {
        given().when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/1").then().statusCode(200);
    }

}
