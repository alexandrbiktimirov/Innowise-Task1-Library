package com.example.authorservice.exception;

public class AuthorDoesNotExistException extends RuntimeException {
    public AuthorDoesNotExistException(String message) {
        super(message);
    }
}
