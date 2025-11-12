package com.example.library.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreCreateDto(
        @NotBlank
        String name
) {
}
