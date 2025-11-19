package com.example.library.exception;

public class BookImageDoesNotExistException extends RuntimeException {
    public BookImageDoesNotExistException(String message) {
        super(message);
    }
}
