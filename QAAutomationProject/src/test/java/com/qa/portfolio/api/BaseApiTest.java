package com.qa.portfolio.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {

    @BeforeClass(alwaysRun = true)
    public void configureApi() {
        Properties properties = new Properties();
        try (InputStream config = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (config == null) {
                throw new IllegalStateException("config.properties was not found on the test classpath");
            }
            properties.load(config);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load config.properties", e);
        }

        String baseUrl = properties.getProperty("apiBaseUrl");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("apiBaseUrl is missing from config.properties");
        }
        RestAssured.baseURI = baseUrl;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
