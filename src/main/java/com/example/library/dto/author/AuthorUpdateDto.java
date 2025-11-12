package com.example.library.dto.author;

import jakarta.annotation.Nullable;

public record AuthorUpdateDto(
        @Nullable
        String firstName,
        @Nullable
        String lastName
) {
}
