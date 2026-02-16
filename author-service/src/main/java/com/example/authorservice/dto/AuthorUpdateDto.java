package com.example.authorservice.dto;

import jakarta.annotation.Nullable;

public record AuthorUpdateDto(
        @Nullable
        String firstName,
        @Nullable
        String lastName
) {
}
