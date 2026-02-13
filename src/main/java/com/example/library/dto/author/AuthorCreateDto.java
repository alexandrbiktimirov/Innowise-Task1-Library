package com.example.library.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AuthorCreateDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotNull
        Set<@Positive Long> bookIds
) {
}
