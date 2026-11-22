package com.qa.portfolio.api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class PostsApiTest extends BaseApiTest {

    @Test(groups = "api", description = "GET /posts returns the observed collection of 100 posts")
    public void getAllPostsReturnsOneHundredPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", equalTo(100));
    }

    @Test(groups = "api", description = "GET /posts/1 returns a post with its required fields")
    public void getPostByIdReturnsRequiredFields() {
        given()
                .when()
                .get("/posts/{id}", ApiTestData.POST_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo(ApiTestData.POST_ID))
                .body("userId", equalTo(ApiTestData.USER_ID))
                .body("title", org.hamcrest.Matchers.notNullValue())
                .body("body", org.hamcrest.Matchers.notNullValue());
    }

    @Test(groups = "api", description = "GET /posts/1 responds as JSON in under two seconds")
    public void getPostResponseIsJsonAndWithinTwoSeconds() {
        given()
                .when()
                .get("/posts/{id}", ApiTestData.POST_ID)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .time(lessThan(2000L));
    }

    @Test(groups = "api", description = "GET /posts/9999 returns the observed not-found response")
    public void getUnknownPostReturnsNotFound() {
        given()
                .when()
                .get("/posts/{id}", ApiTestData.UNKNOWN_POST_ID)
                .then()
                .statusCode(404);
    }

    @Test(groups = "api", description = "GET /posts filtered by userId returns only that user's posts")
    public void getPostsByUserReturnsOnlyMatchingPosts() {
        given()
                .queryParam("userId", ApiTestData.USER_ID)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(ApiTestData.USER_ID)));
    }

    @Test(groups = "api", description = "POST /posts returns 201 and echoes the submitted fields")
    public void createPostReturnsCreatedPostWithoutAssumingPersistence() {
        given()
                .contentType("application/json")
                .body(ApiTestData.NEW_POST)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("title", equalTo(ApiTestData.POST_TITLE))
                .body("body", equalTo(ApiTestData.POST_BODY))
                .body("userId", equalTo(ApiTestData.USER_ID))
                .body("id", org.hamcrest.Matchers.notNullValue());
    }

    @Test(groups = "api", description = "PUT /posts/1 returns the updated title in its response")
    public void updatePostReturnsUpdatedTitleWithoutAssumingPersistence() {
        given()
                .contentType("application/json")
                .body(ApiTestData.UPDATED_POST)
                .when()
                .put("/posts/{id}", ApiTestData.POST_ID)
                .then()
                .statusCode(200)
                .body("title", equalTo(ApiTestData.UPDATED_TITLE));
    }

    @Test(groups = "api", description = "DELETE /posts/1 returns the observed successful status")
    public void deletePostReturnsSuccessWithoutAssumingPersistence() {
        given()
                .when()
                .delete("/posts/{id}", ApiTestData.POST_ID)
                .then()
                .statusCode(200);
    }
}
