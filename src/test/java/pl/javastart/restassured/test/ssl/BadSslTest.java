package pl.javastart.restassured.test.ssl;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BadSslTest {

    @Test
    public void sslCertExpiredTest() {
        given().relaxedHTTPSValidation().when().get("https://expired.badssl.com/").then().statusCode(200);
    }

}
