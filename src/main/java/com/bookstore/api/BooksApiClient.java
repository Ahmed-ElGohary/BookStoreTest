package com.bookstore.api;

import com.bookstore.api.models.Book;
import com.bookstore.api.specs.RequestSpecificationConfig;
import com.bookstore.api.specs.ResponseSpecificationConfig;
import com.bookstore.api.utils.LoggerUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BooksApiClient {
    public Response getAllBooks() {
        LoggerUtil.logInfo("Fetching all books");
        return given(RequestSpecificationConfig.getRequestSpec())
                .when()
                .get()
                .then()
                .spec(ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response getBookById(int id) {
        LoggerUtil.logInfo("Fetching book with ID: " + id);
        return given(RequestSpecificationConfig.getRequestSpec())
                .when()
                .get("/{id}", id)
                .then()
                .spec(id == 9999 ? ResponseSpecificationConfig.getNotFoundResponseSpec() : ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response createBook(Book book) {
        LoggerUtil.logInfo("Creating book: " + book.getTitle());
        return given(RequestSpecificationConfig.getRequestSpec())
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
        return given(RequestSpecificationConfig.getRequestSpec())
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
        return given(RequestSpecificationConfig.getRequestSpec())
                .when()
                .delete("/{id}", id)
                .then()
                .spec(ResponseSpecificationConfig.getSuccessResponseSpec())
                .extract()
                .response();
    }

    public Response createBookRaw(String bookJson) {
        LoggerUtil.logInfo("Creating book with raw JSON");
        return given(RequestSpecificationConfig.getRequestSpec())
                .body(bookJson)
                .when()
                .post();
    }
}