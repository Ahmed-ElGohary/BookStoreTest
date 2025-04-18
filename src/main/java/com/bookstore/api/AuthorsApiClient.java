package com.bookstore.api;

import com.bookstore.api.specs.RequestSpecificationConfig;
import com.bookstore.api.specs.ResponseSpecificationConfig;
import com.bookstore.api.models.Author;
import com.bookstore.api.utils.LoggerUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthorsApiClient {
    private final RequestSpecification requestSpec;

    public AuthorsApiClient() {
        this.requestSpec = RequestSpecificationConfig.getRequestSpec()
                .basePath("/Authors");
    }

    public Response getAllAuthors() {
        LoggerUtil.logInfo("Retrieving all authors");
        return given(requestSpec)
                .when()
                .get()
                .then()
                .spec(ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response getAuthorById(int authorId) {
        LoggerUtil.logInfo("Retrieving author with ID: " + authorId);
        return given(requestSpec)
                .when()
                .get("/" + authorId)
                .then()
                .extract()
                .response();
    }

    public Response createAuthor(Author author) {
        LoggerUtil.logInfo("Creating a new author");
        return given(requestSpec)
                .body(author)
                .when()
                .post()
                .then()
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
                .extract()
                .response();
    }

    public Response updateAuthor(int authorId, Author updatedAuthor) {
        LoggerUtil.logInfo("Updating author with ID: " + authorId);
        return given(requestSpec)
                .body(updatedAuthor)
                .when()
                .put("/" + authorId)
                .then()
                .extract()
                .response();
    }

    public Response deleteAuthor(int authorId) {
        LoggerUtil.logInfo("Deleting author with ID: " + authorId);
        return given(requestSpec)
                .when()
                .delete("/" + authorId)
                .then()
                .extract()
                .response();
    }
}