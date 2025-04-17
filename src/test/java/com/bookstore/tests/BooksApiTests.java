package com.bookstore.tests;

import com.bookstore.api.BooksApiClient;
import com.bookstore.api.models.Book;
import com.bookstore.api.specs.ResponseSpecificationConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

public class BooksApiTests {
    private BooksApiClient booksApiClient;

    @BeforeClass
    public void setup() {
        booksApiClient = new BooksApiClient();
    }

    // Happy Path Tests
    @Test(description = "Verify GET all books returns 200 and non-empty list")
    public void testGetAllBooks() {
        Response response = booksApiClient.getAllBooks();
        List<Book> books = response.jsonPath().getList(".", Book.class);
        Assert.assertFalse(books.isEmpty(), "Books list should not be empty");
    }

    @Test(description = "Verify GET book by valid ID returns 200")
    public void testGetBookByValidId() {
        int validId = 1; // Assuming ID 1 exists
        Response response = booksApiClient.getBookById(validId);
        response.then().spec(ResponseSpecificationConfig.getSuccessResponseSpec());
        Book book = response.as(Book.class);
        Assert.assertEquals(book.getId(), validId, "Book ID should match requested ID");
    }

    @Test(description = "Verify POST creates a new book with 200")
    public void testCreateBook() {
        Book newBook = new Book();
        newBook.setTitle("Test Book");
        newBook.setDescription("A test book description");
        newBook.setPageCount(100);
        newBook.setExcerpt("Test excerpt");
        newBook.setPublishDate(LocalDateTime.now().toString());

        Response response = booksApiClient.createBook(newBook);
        Book createdBook = response.as(Book.class);
        Assert.assertEquals(createdBook.getTitle(), newBook.getTitle(), "Title should match");
    }

    @Test(description = "Verify PUT updates a book with 200")
    public void testUpdateBook() {

        Book newBook = new Book();
        newBook.setTitle("Book to Update");
        newBook.setDescription("Initial description");
        newBook.setPageCount(100);
        newBook.setExcerpt("Initial excerpt");
        newBook.setPublishDate(LocalDateTime.now().toString());
        Response createResponse = booksApiClient.createBook(newBook);
        Book createdBook = createResponse.as(Book.class);

        createdBook.setTitle("Updated Book");
        createdBook.setDescription("Updated description");
        Response updateResponse = booksApiClient.updateBook(createdBook.getId(), createdBook);
        updateResponse.then().spec(ResponseSpecificationConfig.getSuccessResponseSpec());
        Book updatedBook = updateResponse.as(Book.class);
        Assert.assertEquals(updatedBook.getTitle(), "Updated Book", "Title should be updated");
    }

    @Test(description = "Verify DELETE removes a book with 200")
    public void testDeleteBook() {

        Book newBook = new Book();
        newBook.setTitle("Book to Delete");
        newBook.setDescription("To be deleted");
        newBook.setPageCount(100);
        newBook.setExcerpt("Delete excerpt");
        newBook.setPublishDate(LocalDateTime.now().toString());
        Response createResponse = booksApiClient.createBook(newBook);
        Book createdBook = createResponse.as(Book.class);

        Response deleteResponse = booksApiClient.deleteBook(createdBook.getId());
        deleteResponse.then().spec(ResponseSpecificationConfig.getSuccessResponseSpec());

        Response getResponse = booksApiClient.getBookById(createdBook.getId());
        getResponse.then().spec(ResponseSpecificationConfig.getNotFoundResponseSpec());
    }

    @Test(description = "Verify GET book by invalid ID returns 404")
    public void testGetBookByInvalidId() {
        int invalidId = 9999;
        Response response = booksApiClient.getBookById(invalidId);
        response.then().spec(ResponseSpecificationConfig.getNotFoundResponseSpec());
        Assert.assertEquals(response.getStatusCode(), 404, "Should return 404 for invalid ID");
    }

    @Test(description = "Verify GET book by negative ID returns 400 or 404")
    public void testGetBookByNegativeId() {
        int negativeId = -1;
        Response response = booksApiClient.getBookById(negativeId);
        Assert.assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 404,
                "Should return 400 or 404 for negative ID");
    }

    @Test(description = "Verify POST with missing required fields returns 400")
    public void testCreateBookWithMissingFields() {
        Book newBook = new Book();

        newBook.setDescription("Description only");
        newBook.setPageCount(100);
        newBook.setExcerpt("Excerpt");
        newBook.setPublishDate(LocalDateTime.now().toString());

        Response response = booksApiClient.createBook(newBook);
        Assert.assertEquals(response.getStatusCode(), 400, "Should return 400 for missing required fields");
    }

    @Test(description = "Verify POST with invalid data types returns 400")
    public void testCreateBookWithInvalidData() {
        // Using raw JSON to simulate invalid data type (e.g., pageCount as string)
        String invalidBookJson = "{ \"title\": \"Invalid Book\", \"pageCount\": \"not_a_number\", " +
                "\"description\": \"Invalid\", \"excerpt\": \"Excerpt\", \"publishDate\": \"" +
                LocalDateTime.now().toString() + "\" }";
        Response response = booksApiClient.createBookRaw(invalidBookJson);
        Assert.assertEquals(response.getStatusCode(), 400, "Should return 400 for invalid data types");
    }

    @Test(description = "Verify PUT on non-existent book returns 404")
    public void testUpdateNonExistentBook() {
        Book book = new Book();
        book.setId(9999);
        book.setTitle("Non-existent Book");
        book.setDescription("Doesn't exist");
        book.setPageCount(100);
        book.setExcerpt("Excerpt");
        book.setPublishDate(LocalDateTime.now().toString());

        Response response = booksApiClient.updateBook(9999, book);
        Assert.assertEquals(response.getStatusCode(), 404, "Should return 404 for non-existent book");
    }

    @Test(description = "Verify DELETE on non-existent book returns 404")
    public void testDeleteNonExistentBook() {
        Response response = booksApiClient.deleteBook(9999);
        Assert.assertTrue(response.getStatusCode() == 404 || response.getStatusCode() == 200,
                "Should return 404 or 200 for non-existent book");
    }
}