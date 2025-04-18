package com.bookstore.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {
    @JsonProperty("id")
    private int id;

    @JsonProperty("idBook")
    private int idBook;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    public Author() {}


    public Author(int id, int idBook, String firstName, String lastName) {
        this.id = id;
        this.idBook = idBook;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdBook() { return idBook; }
    public void setIdBook(int idBook) { this.idBook = idBook; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }


    @Override
    public String toString() {
        return "Author{id=" + id + ", idBook=" + idBook + ", firstName='" + firstName + "', lastName='" + lastName + "'}";
    }
}