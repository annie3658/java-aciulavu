package com.library.application.exception;

public class CoverNotFoundException extends RuntimeException {
    public CoverNotFoundException(String cover) {
        super("Cover " + cover + " not found");
    }
}
