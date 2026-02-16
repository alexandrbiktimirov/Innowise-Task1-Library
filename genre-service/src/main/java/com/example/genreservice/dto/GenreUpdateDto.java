package com.example.genreservice.dto;

import jakarta.validation.constraints.NotBlank;

public record GenreUpdateDto(
        @NotBlank
        String name
) {
}
