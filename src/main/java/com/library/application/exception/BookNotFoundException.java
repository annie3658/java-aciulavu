package com.library.application.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String book) {
        super("Book " + book + " was not found");
    }
}
