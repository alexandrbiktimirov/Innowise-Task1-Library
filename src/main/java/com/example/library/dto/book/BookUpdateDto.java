package com.example.library.dto.book;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record BookUpdateDto(
        @NotBlank
        String title,
        @Positive
        long authorId,
        @NotBlank
        String description,
        @Positive
        long genreId
) {
}
