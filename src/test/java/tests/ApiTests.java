package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.core.Is.is;

public class ApiTests {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void checkGetListUserResponseTest(){
        given()
                .log()
                .all()
                .contentType(JSON)
                .accept(JSON)
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(SC_OK)
                .body("data.id[1]", is(8),
                        "data.email[1]", is("lindsay.ferguson@reqres.in"),
                        "data.first_name[1]", is("Lindsay"),
                        "data.last_name[1]", is("Ferguson"),
                        "data.avatar[1]", is("https://reqres.in/img/faces/8-image.jpg"));
    }

    @Test
    void checkPostCreateResponseTest(){
        String data = "{     \"name\": \"morpheus\",     \"job\": \"leader\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/users")
                .then()
                .statusCode(SC_CREATED)
                .body("name", is("morpheus"),
                        "job", is("leader"));
    }


    @Test
    void checkPutUpdateResponseTest(){
        String data = "{\"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(SC_OK)
                .body("name", is("morpheus"),
                        "job", is("zion resident"));
    }

    @Test
    void checkPostRegisterBadRequestResponseTest(){
        String data = "{\"email\": \"sydney@fife\"}";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("error", is("Missing password"));
    }

    @Test
    void checkPostRegisterSuccessResponseTest(){
        String data = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(SC_OK)
                .body("error", is("Missing password"));
    }

}
