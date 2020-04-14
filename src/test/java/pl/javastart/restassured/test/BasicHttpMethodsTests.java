package pl.javastart.restassured.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BasicHttpMethodsTests {

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {

        String pet = "{\n" +
                "  \"id\": 123,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"dogs\"\n" +
                "  },\n" +
                "  \"name\": \"Burek\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"http://photos.com/dog1.jpg\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"dogs-category\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        given().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().statusCode(200);
    }

}
