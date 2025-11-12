package com.example.library.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreUpdateDto(
        @NotBlank
        String name
) {
}
