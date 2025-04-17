# Bookstore API Test Framework

## Overview
This project is an automated test framework for the Bookstore API (using FakeRestAPI: `https://fakerestapi.azurewebsites.net/api/v1/Books`) and Authors API. It uses **Rest Assured** for API testing, **TestNG** for test execution, and **ExtentReports** for reporting.

## Project Structure
- `src/main/java/`
  - `com.bookstore.api.config`: Configuration classes (e.g., `ConfigManager`).
  - `com.bookstore.api.models`: POJOs (e.g., `Book`, `Author`).
  - `com.bookstore.api.specs`: Response specifications (e.g., `ResponseSpecificationConfig`).
  - `com.bookstore.api`: API clients (e.g., `BooksApiClient`, `AuthorsApiClient`).
  - `com.bookstore.utils`: Utilities (e.g., `JsonUtil`, `LoggerUtil`).
- `src/test/java/`
  - `com.bookstore.tests`: Test classes (e.g., `BooksApiTests`, `AuthorsApiTests`).
  - `com.bookstore.listeners`: TestNG listeners (e.g., `TestListener` for reporting).
- `src/test/resources/`
  - `testng.xml`: TestNG suite configuration.
- `src/main/resources/`
  - `config.properties`: Configuration file with `base.url`.
- `reports/extent-reports/`: Generated test reports (`extent-report.html`).
- `logs/`: Log files (`test.log`).

## Prerequisites
- **Java 11** (or JDK 24 if updated in `pom.xml`).
- **Maven**: For dependency management and test execution.
- **IntelliJ IDEA** (optional, for development).

## Setup
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd ApiAutomation
   ```
2. **Install Dependencies**:
   ```bash
   mvn clean install
   ```
3. **Verify `config.properties`**:
   - Ensure `src/main/resources/config.properties` exists with:
     ```
     base.url=https://fakerestapi.azurewebsites.net/api/v1/Books
     ```

## Running Tests
- **Via Terminal**:
  ```bash
  mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml
  ```
- **Via IntelliJ**:
  - Right-click `src/test/resources/testng.xml` > "Run ‘testng.xml’".

## Test Coverage
- **Books API**:
  - Happy Path: GET all books, GET by ID, POST create, PUT update, DELETE.
  - Edge Cases: Invalid ID, missing fields, invalid data types, non-existent resources.
- **Authors API**:
  - Similar coverage as Books API.

## Reports
- After tests run, find the report at `reports/extent-reports/extent-report.html`.
- Open in a browser to view test results.

## Logs
- Check `logs/test.log` for detailed execution logs.

## Troubleshooting
- **Tests Fail**: Verify the API URL in `config.properties` is accessible.
- **No Report Generated**: Ensure `TestListener` is configured in `testng.xml` or test classes.
- **Maven Errors**: Run `mvn clean install` to resolve dependency issues.