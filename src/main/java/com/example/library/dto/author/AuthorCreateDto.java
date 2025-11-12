package com.example.library.dto.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}
