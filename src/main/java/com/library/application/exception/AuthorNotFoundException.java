package com.library.application.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String author) {
        super("Author  " + author + " not found");
    }
}
