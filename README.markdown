# Bookstore API Testing

## Overview

This project is an automated API testing framework for the FakeRestAPI Books and Authors endpoints. Built with Java, Maven, TestNG, and REST Assured, it validates the functionality of CRUD operations for Books (`/api/v1/Books`) and Authors (`/api/v1/Authors`) APIs. The framework covers both happy path scenarios (successful operations) and edge cases (error handling and invalid inputs) to ensure robust API behavior.

## Features

- **Books API Testing**: Tests for creating, retrieving, updating, and deleting books, including validation of response payloads and status codes.
- **Authors API Testing**: Tests for creating, retrieving, updating, and deleting authors, including handling of related book IDs and error scenarios.
- **Happy Path Cases**: Validates successful API operations with valid data.
- **Edge Cases**: Tests invalid inputs, missing fields, non-existent resources, and malformed JSON.
- **Logging**: Comprehensive logging of requests and responses for debugging.
- **CI Integration**: GitHub Actions workflow runs tests on push/pull requests, generating test reports and logs as artifacts.

## Prerequisites

- **Java**: JDK 11 or higher
- **Maven**: 3.6.0 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or similar
- **Git**: For cloning the repository
- **Internet Access**: To interact with FakeRestAPI endpoints

## Setup Instructions

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Ahmed-ElGohary/BookStoreTest.git
   cd BookStoreTest
   ```

2. **Install Dependencies**: Run Maven to download required libraries:

   ```bash
   mvn clean install
   ```

3. **Configure Environment**:

   - Ensure `config.properties` (in `src/main/resources`) has the correct base URL:

     ```
     baseUrl=https://fakerestapi.azurewebsites.net
     ```
   - No additional configuration is needed unless custom endpoints are used.

4. **Run Tests**: Execute all tests using Maven:

   ```bash
   mvn clean test
   ```

   Test reports are generated in `target/surefire-reports/` (TestNG reports) and `target/extent-reports/extent.html` (Extent Report).

## Test Coverage

The framework includes comprehensive test cases for the Books and Authors APIs, covering both happy and edge scenarios.

### Books API Tests (`BooksApiTests.java`)

- **Happy Path**:
  - `testGetAllBooks`: Retrieves all books and verifies a non-empty list (200 status).
  - `testGetBookByValidId`: Fetches a book by valid ID (e.g., 1) and validates the response (200 status).
  - `testCreateBook`: Creates a book with valid data and checks the response payload (200 status).
  - `testUpdateBook`: Updates an existing book and verifies changes (200 status).
  - `testDeleteBook`: Deletes a book and confirms it’s no longer accessible (200 status, expecting 404 in a real API).
- **Edge Cases**:
  - `testGetBookByNegativeId`: Attempts to fetch a book with a negative ID (404 status).
  - `testCreateBookWithMissingFields`: Creates a book with partial data (200 status, API accepts it).
  - `testCreateBookWithInvalidData`: Sends invalid data (e.g., non-numeric `pageCount`) (400 status).
  - `testDeleteNonExistentBook`: Deletes a non-existent book (200 status, expecting 404 in a real API).

### Authors API Tests (`AuthorsApiTests.java`)

- **Happy Path**:
  - `testGetAllAuthors`: Retrieves all authors and verifies a non-empty list (200 status).
  - `testCreateAuthor`: Creates an author with valid data (e.g., `idBook=1`, `firstName`, `lastName`) (200 status).
  - `testGetAuthorById`: Fetches an author by valid ID and validates the response (200 status).
  - `testUpdateAuthor`: Updates an existing author and verifies changes (200 status).
  - `testDeleteAuthor`: Deletes an author and checks the response (200 status, expecting 404 in a real API).
- **Edge Cases**:
  - `testGetAuthorWithInvalidId`: Attempts to fetch an author with an invalid ID (404 status).
  - `testCreateAuthorWithMissingFields`: Creates an author with partial data (200 status, API accepts it).
  - `testCreateAuthorWithInvalidJson`: Sends malformed JSON (400 status).
  - `testUpdateNonExistentAuthor`: Updates a non-existent author (200 status, expecting 404 in a real API).
  - `testDeleteNonExistentAuthor`: Deletes a non-existent author (200 status, expecting 404 in a real API).

## Project Structure

```
BookStoreTest/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com.bookstore.api/
│   │   │   │   ├── BooksApiClient.java
│   │   │   │   ├── AuthorsApiClient.java
│   │   │   │   ├── models/
│   │   │   │   │   ├── Book.java
│   │   │   │   │   ├── Author.java
│   │   │   ├── com.bookstore.utils/
│   │   │   │   ├── LoggerUtil.java
│   │   │   ├── com.bookstore.api.specs/
│   │   │   │   ├── RequestSpecificationConfig.java
│   │   │   │   ├── ResponseSpecificationConfig.java
│   │   ├── resources/
│   │   │   ├── config.properties
│   ├── test/
│   │   ├── java/
│   │   │   ├── com.bookstore.tests/
│   │   │   │   ├── BooksApiTests.java
│   │   │   │   ├── AuthorsApiTests.java
├── pom.xml
├── README.md
├── .github/
│   ├── workflows/
│   │   ├── ci.yml
```

## Continuous Integration

The project uses GitHub Actions for continuous integration (see `.github/workflows/ci.yml`). The workflow runs automatically on push or pull requests to the `master` branch and performs the following:

- Checks out the code.
- Sets up JDK 11.
- Runs `mvn clean test -Dmaven.test.failure.ignore=true` to execute all tests, ignoring failures.
- Uploads test reports and logs as artifacts.

### Accessing Reports and Logs

The workflow generates the following artifacts, available in the Actions tab (`https://github.com/Ahmed-ElGohary/BookStoreTest/actions`):

- **Extent Report** (`extent-report` artifact): A detailed HTML report (`target/extent-reports/extent.html`) summarizing test results for Books and Authors APIs.
- **Surefire Reports** (`surefire-reports` artifact): TestNG reports (`target/surefire-reports/`) with detailed test execution logs.
- **Maven Logs** (`maven-logs` artifact): Full console output from the `mvn clean test` command (`maven-logs.txt`).

To view them:
1. Go to the Actions tab and select the latest workflow run.
2. Scroll to the "Artifacts" section and download `extent-report`, `surefire-reports`, and `maven-logs`.
3. Unzip and open `extent.html` in a browser for the Extent Report, or review `surefire-reports/TEST-*.xml` for test details.

### Note on Test Failures

Some tests fail due to FakeRestAPI’s mock behavior (e.g., DELETE operations return 200 instead of 404). The workflow uses `-Dmaven.test.failure.ignore=true` to complete successfully, allowing report generation. These failures highlight API issues, and full test coverage is documented in the Extent Report.

## Troubleshooting

- **Test Failures**: Check `surefire-reports` or `extent-report` artifacts for detailed logs. Common issues include API downtime or mismatched expectations with FakeRestAPI.
- **Dependency Issues**: Run `mvn clean install` to refresh dependencies.
- **GitHub Actions Issues**: Verify `.github/workflows/ci.yml` exists and Actions permissions are enabled in repository settings (Settings > Actions > General).

## Contributing

Feel free to fork the repository, add new test cases, or improve the framework. Submit pull requests to the `master` branch for review.

## License

This project is licensed under the MIT License.