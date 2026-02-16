package com.example.genreservice.dto;

import jakarta.validation.constraints.NotBlank;

public record GenreCreateDto(
        @NotBlank
        String name
) {
}
