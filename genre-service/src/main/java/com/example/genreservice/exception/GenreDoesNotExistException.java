package com.example.genreservice.exception;

public class GenreDoesNotExistException extends RuntimeException {
    public GenreDoesNotExistException(String message) {
        super(message);
    }
}
