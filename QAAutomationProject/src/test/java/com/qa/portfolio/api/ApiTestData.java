package com.qa.portfolio.api;

import java.util.Map;

final class ApiTestData {

    static final int POST_ID = 1;
    static final int UNKNOWN_POST_ID = 9999;
    static final int USER_ID = 1;
    static final String POST_TITLE = "API test post";
    static final String POST_BODY = "Created by the API regression suite";
    static final String UPDATED_TITLE = "Updated API test post";
    static final String UPDATED_BODY = "Updated by the API regression suite";
    static final Map<String, Object> NEW_POST = Map.of(
            "title", POST_TITLE,
            "body", POST_BODY,
            "userId", USER_ID);
    static final Map<String, Object> UPDATED_POST = Map.of(
            "id", POST_ID,
            "title", UPDATED_TITLE,
            "body", UPDATED_BODY,
            "userId", USER_ID);

    private ApiTestData() {
    }
}
