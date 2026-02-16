package com.example.bookservice.exception;

public class BookImageDoesNotExistException extends RuntimeException {
    public BookImageDoesNotExistException(String message) {
        super(message);
    }
}
