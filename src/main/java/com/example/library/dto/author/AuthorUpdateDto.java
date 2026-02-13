package com.example.library.dto.author;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record AuthorUpdateDto(
        @Nullable
        String firstName,
        @Nullable
        String lastName,
        @Nullable
        Set<@Positive Long> bookIds
) {
}
