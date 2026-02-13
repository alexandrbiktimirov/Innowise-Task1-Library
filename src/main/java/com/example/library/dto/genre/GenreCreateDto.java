package com.example.library.dto.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record GenreCreateDto(
        @NotBlank
        String name,
        @NotNull
        Set<@Positive Long> bookIds
) {
}
