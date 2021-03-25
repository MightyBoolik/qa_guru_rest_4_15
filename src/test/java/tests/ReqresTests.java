package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ReqresTests {
    @BeforeAll
    static void setup() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void singleResourceRequestTest() {
        given()
                .when()
                .get("/api/unknown/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("support.url",
                        is("https://reqres.in/#support-heading"));

    }
}
