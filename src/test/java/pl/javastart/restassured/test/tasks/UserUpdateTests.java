package pl.javastart.restassured.test.tasks;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.user.User;
import pl.javastart.restassured.test.testng.TestListener;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Listeners(TestListener.class)
public class UserUpdateTests extends TestBase {

    @Test
    public void givenCorrectUserDataWhenFirstNameLastNameAreUpdatedThenUserDataIsUpdatedTest() {

        //Tworzymy specyfikację żądania, jest ona następnie użyta dla wszystkich żądań
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setContentType("application/json");
        RequestSpecification defaultRequestSpecification = requestSpecBuilder.build();

        User user = new User();
        user.setId(445);
        user.setUsername("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(123);

        // Tworzymy specifikację odpowiedzi dla żądań metodą POST oraz PUT
        ResponseSpecBuilder postResponseSpecBuilder = new ResponseSpecBuilder();
        postResponseSpecBuilder
                .expectBody("code", equalTo(200))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", equalTo("445"))
                .expectStatusCode(200);
        ResponseSpecification userCreationResponseSpecification = postResponseSpecBuilder.build();

        given().spec(defaultRequestSpecification)
                .body(user)
                .when().post("user")
                .then().assertThat().spec(userCreationResponseSpecification);

        user.setFirstName("Adam");
        user.setLastName("Malinowski");

        given().spec(defaultRequestSpecification)
                .pathParam("username", user.getUsername())
                .body(user)
                .when().put("user/{username}")
                .then().assertThat().spec(userCreationResponseSpecification);

        // Tworzymy specyfikację odpowiedzi dla żądania metodą GET
        ResponseSpecBuilder updateResponseSpecBuilder = new ResponseSpecBuilder();
        updateResponseSpecBuilder
                .expectBody("id", equalTo(445))
                .expectBody("username", equalTo("firstuser"))
                .expectBody("firstName", equalTo("Adam"))
                .expectBody("lastName", equalTo("Malinowski"))
                .expectBody("email", equalTo("krzysztof@test.com"))
                .expectBody("password", equalTo("password"))
                .expectBody("phone", equalTo("+123456789"))
                .expectBody("userStatus", equalTo(123))
                .expectStatusCode(200);
        ResponseSpecification getResponseSpecification = updateResponseSpecBuilder.build();

        given().spec(defaultRequestSpecification)
                .pathParam("username", user.getUsername())
                .when().get("user/{username}")
                .then().assertThat().spec(getResponseSpecification);
    }

}
