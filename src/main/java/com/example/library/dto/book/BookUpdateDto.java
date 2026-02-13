package com.example.library.dto.book;

import jakarta.validation.constraints.Positive;
import jakarta.annotation.Nullable;

import java.util.Set;

public record BookUpdateDto(
        @Nullable
        String title,
        @Nullable
        String description,
        @Nullable
        Set<@Positive Long> authorIds,
        @Nullable
        Set<@Positive Long> genreIds
) {
}
