package com.example.library.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record BookCreateDto(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotEmpty
        Set<@Positive Long> authorIds,
        @NotEmpty
        Set<@Positive Long> genreIds
) {
}
