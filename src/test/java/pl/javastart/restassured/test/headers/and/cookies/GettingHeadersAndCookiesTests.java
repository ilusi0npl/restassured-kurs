package pl.javastart.restassured.test.headers.and.cookies;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class GettingHeadersAndCookiesTests {

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {

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

        Response response = given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().extract().response();

        int statusCode = response.getStatusCode();
        String statusLine = response.getStatusLine();
        Headers responseHeaders = response.getHeaders();
        Map<String, String> cookies = response.getCookies();

        assertEquals(statusLine, "HTTP/1.1 200 OK", "Status line");
        assertEquals(statusCode, 200, "Status code");
        assertNotNull(responseHeaders.get("Date"));
        assertEquals(responseHeaders.get("Content-Type").getValue(), "application/json", "Content type header");
        assertEquals(responseHeaders.get("Server").getValue(), "nginx/1.10.3", "Server header");
        assertTrue(cookies.isEmpty(), "Cookies are empty");
    }

}
