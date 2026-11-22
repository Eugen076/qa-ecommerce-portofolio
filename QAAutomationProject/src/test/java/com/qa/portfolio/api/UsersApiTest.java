package com.qa.portfolio.api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesRegex;

public class UsersApiTest extends BaseApiTest {

    @Test(groups = "api", description = "GET /users/1 returns a user with a valid email address")
    public void getUserByIdReturnsValidEmail() {
        given()
                .when()
                .get("/users/{id}", ApiTestData.USER_ID)
                .then()
                .statusCode(200)
                .body("email", matchesRegex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }
}
