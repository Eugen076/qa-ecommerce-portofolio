package com.qa.portfolio.api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;

public class CommentsApiTest extends BaseApiTest {

    @Test(groups = "api", description = "GET /posts/1/comments returns comments for post 1")
    public void getCommentsForPostReturnsMatchingPostIds() {
        given()
                .when()
                .get("/posts/{id}/comments", ApiTestData.POST_ID)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(ApiTestData.POST_ID)));
    }
}
