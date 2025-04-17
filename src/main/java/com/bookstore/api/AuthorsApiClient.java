package com.bookstore.api;

import com.bookstore.api.specs.RequestSpecificationConfig;
import com.bookstore.api.specs.ResponseSpecificationConfig;

import com.bookstore.api.utils.LoggerUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthorsApiClient {
    private final RequestSpecification requestSpec;

    public AuthorsApiClient() {
        this.requestSpec = RequestSpecificationConfig.getRequestSpec()
                .basePath("/Authors"); // Correct base path for Authors API
    }

    public Response getAllAuthors() {
        LoggerUtil.logInfo("Retrieving all authors");
        return given(requestSpec)
                .when()
                .get()
                .then()
                .spec(ResponseSpecificationConfig.getSuccessResponseSpec())
                .log().ifError()
                .extract()
                .response();
    }

    public Response getAuthorById(int authorId) {
        LoggerUtil.logInfo("Retrieving author with ID: " + authorId);
        return given(requestSpec)
                .when()
                .get("/" + authorId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response createAuthor(Object author) {
        LoggerUtil.logInfo("Creating a new author");
        return given(requestSpec)
                .body(author)
                .when()
                .post()
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response createAuthorRaw(String authorJson) {
        LoggerUtil.logInfo("Creating author with raw JSON");
        return given(requestSpec)
                .body(authorJson)
                .when()
                .post()
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response updateAuthor(int authorId, Object updatedAuthor) {
        LoggerUtil.logInfo("Updating author with ID: " + authorId);
        return given(requestSpec)
                .body(updatedAuthor)
                .when()
                .put("/" + authorId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public Response deleteAuthor(int authorId) {
        LoggerUtil.logInfo("Deleting author with ID: " + authorId);
        return given(requestSpec)
                .when()
                .delete("/" + authorId)
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}