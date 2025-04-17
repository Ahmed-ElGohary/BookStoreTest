package com.bookstore.api.specs;


import com.bookstore.api.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RequestSpecificationConfig {
    private static final String BASE_URL = ConfigManager.getBaseUrl();

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(JSON)
                .build();
    }

    // Optional: Add method for authenticated requests (if needed in the future)
    public static RequestSpecification getAuthenticatedRequestSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}