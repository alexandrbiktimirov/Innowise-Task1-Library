package com.example.bookservice.dto;

import com.example.library.dto.genre.GenreDto;
import com.example.library.dto.author.AuthorDto;

public record BookDto(
        String title,
        AuthorDto author,
        String description,
        GenreDto genre
) {
}
