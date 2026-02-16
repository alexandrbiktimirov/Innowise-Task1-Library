package com.example.authorservice.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}
