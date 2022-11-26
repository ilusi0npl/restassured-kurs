package pl.javastart.restassured.test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FirstRestAssuredAutomatedTest {

    @Test
    public void givenNonExistingPetIdWhenGetPetThenPetNotFoundTest() {

        // Metoda given() tworzy specyfikację żądania  - Request Specification
        RequestSpecification given = given();

        // Metoda when() - zwraca samą siebie, czyli aktualną specyfikację żądania, tutaj pełni role spoiwa, sekcji given z then
        RequestSpecification when = given.when();

        // Metoda get(), wykonuje żądanie HTTP przy użyciu metody HTTP GET
        Response response = when.get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/0");

        // Metoda then() Bieże odpowiedź i zamienia ją w obiekt ValidatableResponse, na którym możemy wykonać asercje wbudowane we framework REST Assured
        ValidatableResponse then = response.then();

        // Wykonujemy asercję przy użyciu metody statusCode(), sprawdzając, że kod statusowy jest równy 404
        then.statusCode(404);
    }

}
