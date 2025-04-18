package com.bookstore.tests;

import com.bookstore.api.BooksApiClient;
import com.bookstore.api.models.Book;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.bookstore.constants.HttpStatusCodes.*;

public class BooksApiTests {
    private BooksApiClient booksApiClient;
    private int createdBookId;

    @BeforeClass
    public void setup() {
        booksApiClient = new BooksApiClient();
    }

    @Test
    public void testGetAllBooks() {
        Response response = booksApiClient.getAllBooks();
        Assert.assertEquals(response.getStatusCode(), OK, "Expected status code 200");
        Assert.assertTrue(response.jsonPath().getList("").size() > 0, "Books list should not be empty");
    }

    @Test
    public void testGetBookByValidId() {
        Response response = booksApiClient.getBookById(1);
        Assert.assertEquals(response.getStatusCode(), OK, "Expected status code 200");
        Assert.assertEquals(response.jsonPath().getInt("id"), 1, "Book ID should match");
    }

    @Test
    public void testCreateBook() {
        Book newBook = new Book();
        newBook.setId(0);
        newBook.setTitle("Test Book");
        newBook.setDescription("Test Description");
        newBook.setPageCount(100);
        newBook.setExcerpt("Test Excerpt");
        newBook.setPublishDate("2024-01-01T00:00:00Z");

        Response response = booksApiClient.createBook(newBook);
        Assert.assertEquals(response.getStatusCode(), OK, "Expected status code 200 ");

        createdBookId = response.jsonPath().getInt("id");
        Assert.assertNotNull(createdBookId, "Created book ID should not be null");
        Assert.assertEquals(response.jsonPath().getString("title"), "Test Book", "Title should match");
    }

    @Test(dependsOnMethods = "testCreateBook")
    public void testUpdateBook() {
        Book updatedBook = new Book();
        updatedBook.setId(createdBookId);
        updatedBook.setTitle("Updated Book");
        updatedBook.setDescription("Updated Description");
        updatedBook.setPageCount(150);
        updatedBook.setExcerpt("Updated Excerpt");
        updatedBook.setPublishDate("2024-01-02T00:00:00Z");

        Response response = booksApiClient.updateBook(createdBookId, updatedBook);
        Assert.assertEquals(response.getStatusCode(), OK, "Expected status code 200");
        Assert.assertEquals(response.jsonPath().getString("title"), "Updated Book", "Updated title should match");
    }

    @Test(dependsOnMethods = "testCreateBook")
    public void testDeleteBook() {
        Response response = booksApiClient.deleteBook(createdBookId);
        Assert.assertEquals(response.getStatusCode(), OK, "Expected status code 200");
    }

    @Test
    public void testGetBookByNegativeId() {
        Response response = booksApiClient.getBookById(-1);
        Assert.assertEquals(response.getStatusCode(), NOT_FOUND , "Expected status code 404 for negative book ID");
    }

    @Test
    public void testCreateBookWithMissingFields() {
        String invalidBookJson = "{\"title\": \"Missing Fields\"}";
        Response response = booksApiClient.createBookRaw(invalidBookJson);
        Assert.assertEquals(response.getStatusCode(), BAD_REQUEST, "expected status code 400 for missing data");
    }

    @Test
    public void testCreateBookWithInvalidData() {
        String invalidBookJson = "{\"title\": \"Invalid\", \"pageCount\": \"not_a_number\"}";
        Response response = booksApiClient.createBookRaw(invalidBookJson);
        Assert.assertEquals(response.getStatusCode(), BAD_REQUEST, "Expected status code 400 for invalid data");
    }

    @Test
    public void testDeleteNonExistentBook() {
        Response response = booksApiClient.deleteBook(999999);
        Assert.assertEquals(response.getStatusCode(), NOT_FOUND, "expected status code 404 ");
    }
}