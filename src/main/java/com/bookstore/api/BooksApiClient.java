package com.bookstore.api;

import com.bookstore.api.models.Book;
import com.bookstore.api.specs.RequestSpecificationConfig;
import com.bookstore.api.specs.ResponseSpecificationConfig;
import com.bookstore.api.utils.LoggerUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BooksApiClient {
    private final RequestSpecification requestSpec;
    public BooksApiClient() {
        this.requestSpec = RequestSpecificationConfig.getRequestSpec()
                .basePath("/Books");
    }

    public Response getAllBooks() {
        LoggerUtil.logInfo("Fetching all books");
        return given(requestSpec)
                .when()
                .get()
                .then()
                .spec(ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response getBookById(int id) {
        LoggerUtil.logInfo("Fetching book with ID: " + id);
        return given(requestSpec)
                .when()
                .get("/{id}", id)
                .then()
                .spec(id == 9999 ? ResponseSpecificationConfig.getNotFoundResponseSpec() : ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response createBook(Book book) {
        LoggerUtil.logInfo("Creating book: " + book.getTitle());
        return given(requestSpec)
                .body(book)
                .when()
                .post()
                .then()
                .spec(ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response updateBook(int id, Book book) {
        LoggerUtil.logInfo("Updating book with ID: " + id);
        return given(requestSpec)
                .body(book)
                .when()
                .put("/{id}", id)
                .then()
                .spec(ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response deleteBook(int id) {
        LoggerUtil.logInfo("Deleting book with ID: " + id);
        return given(requestSpec)
                .when()
                .delete("/{id}", id)
                .then()
                .spec(ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response createBookRaw(String bookJson) {
        LoggerUtil.logInfo("Creating book with raw JSON");
        return given(requestSpec)
                .body(bookJson)
                .when()
                .post();
    }
}