package com.library.application.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String id) {
        super("Author with id " + id + " not found");
    }
}
