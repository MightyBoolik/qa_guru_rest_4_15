package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static Utils.FileUtil.readStringFromFile;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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

    @Test
    void requestApiUsersTest() {
        String data = readStringFromFile("./src/test/resources/Login_data.txt");
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("id", is(notNullValue()));
    }

    @Test
    void succesfullLoginTest() {
        String data1 = readStringFromFile("./src/test/resources/For_login.txt");
        given()
                .contentType(JSON)
                .body(data1)
                .when()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    void updateRequestTest() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\"}")
                .when()
                .put("/api/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("job", is(notNullValue()));
    }

    @Test
    void unsuccesLoginTest() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"peter@klaven\"}")
                .when()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
