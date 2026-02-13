package com.example.library.dto.genre;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record GenreUpdateDto(
        @Nullable
        String name,
        @Nullable
        Set<@Positive Long> bookIds
) {
}
