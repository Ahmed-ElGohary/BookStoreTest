package com.bookstore.api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class ResponseSpecificationConfig {
    public static ResponseSpecification getSuccessResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(JSON)
                .expectBody("id", notNullValue())
                .build();
    }

    public static ResponseSpecification getNotFoundResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static ResponseSpecification getBadRequestResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification getEmptyBodySuccessSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                // No content type or body expectation
                .build();
    }


    public static ResponseSpecification getBookSuccessResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(JSON)
                .expectBody("title", notNullValue())
                .expectBody("pageCount", notNullValue())
                .build();
    }
}