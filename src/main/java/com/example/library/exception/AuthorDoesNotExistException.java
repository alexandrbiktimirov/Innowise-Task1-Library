package com.example.library.exception;

public class AuthorDoesNotExistException extends RuntimeException {
    public AuthorDoesNotExistException(String message) {
        super(message);
    }
}
