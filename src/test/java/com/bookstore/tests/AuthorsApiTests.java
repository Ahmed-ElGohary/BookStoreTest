package com.bookstore.tests;

import com.bookstore.api.AuthorsApiClient;
import com.bookstore.api.models.Author;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AuthorsApiTests {
    private AuthorsApiClient authorsApiClient;
    private int authorIdToUse = 1;

    @BeforeClass
    public void setup() {
        authorsApiClient = new AuthorsApiClient();
    }

    @Test
    public void testGetAllAuthors() {
        Response response = authorsApiClient.getAllAuthors();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertTrue(response.jsonPath().getList("").size() > 0, "Authors list should not be empty");
    }

    @Test
    public void testCreateAuthor() {
        Author newAuthor = new Author(0, 1, "Test", "Author");
        Response response = authorsApiClient.createAuthor(newAuthor);
        System.out.println("Create Author Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 (FakeRestAPI behavior)");

        int createdId = response.jsonPath().getInt("id");
        System.out.println("Created Author ID: " + createdId);
        Assert.assertNotNull(createdId, "Created author ID should not be null");
        Assert.assertEquals(response.jsonPath().getString("firstName"), "Test", "First name should match");
    }

    @Test
    public void testGetAuthorById() {
        System.out.println("Retrieving author with ID: " + authorIdToUse);
        Response response = authorsApiClient.getAuthorById(authorIdToUse);
        System.out.println("Get Author Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertEquals(response.jsonPath().getInt("id"), authorIdToUse, "Author ID should match");
    }

    @Test(dependsOnMethods = "testGetAuthorById")
    public void testUpdateAuthor() {
        Author updatedAuthor = new Author(authorIdToUse, 2, "Updated", "Author");
        Response response = authorsApiClient.updateAuthor(authorIdToUse, updatedAuthor);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertEquals(response.jsonPath().getString("firstName"), "Updated", "Updated first name should match");
        Assert.assertEquals(response.jsonPath().getInt("idBook"), 2, "Updated book ID should match");
    }

    @Test(dependsOnMethods = "testUpdateAuthor")
    public void testDeleteAuthor() {
        Response response = authorsApiClient.deleteAuthor(authorIdToUse);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 (FakeRestAPI behavior)");

        Response getResponse = authorsApiClient.getAuthorById(authorIdToUse);
        Assert.assertEquals(getResponse.getStatusCode(), 200, "FakeRestAPI returns 200 after deletion (expected 404 in a real API)");
    }

    @Test
    public void testGetAuthorWithInvalidId() {
        Response response = authorsApiClient.getAuthorById(999999);
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 for invalid author ID");
    }

    @Test
    public void testCreateAuthorWithMissingFields() {
        String invalidAuthorJson = "{\"firstName\": \"Missing\"}";
        Response response = authorsApiClient.createAuthorRaw(invalidAuthorJson);
        Assert.assertEquals(response.getStatusCode(), 200, "FakeRestAPI accepts partial data");
    }

    @Test
    public void testCreateAuthorWithInvalidJson() {
        String invalidJson = "{invalid json}";
        Response response = authorsApiClient.createAuthorRaw(invalidJson);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected status code 400 for malformed JSON");
    }

    @Test
    public void testUpdateNonExistentAuthor() {
        Author updatedAuthor = new Author(999999, 1, "Non", "Existent");
        Response response = authorsApiClient.updateAuthor(999999, updatedAuthor);
        Assert.assertEquals(response.getStatusCode(), 200, "FakeRestAPI returns 200 for non-existent author (expected 404 in a real API)");
    }

    @Test
    public void testDeleteNonExistentAuthor() {
        Response response = authorsApiClient.deleteAuthor(999999);
        Assert.assertEquals(response.getStatusCode(), 200, "FakeRestAPI returns 200 for non-existent author (expected 404 in a real API)");
    }
}