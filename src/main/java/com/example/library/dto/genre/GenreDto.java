package com.example.library.dto.genre;

import com.example.library.dto.book.BookSummaryDto;

import java.util.Set;

public record GenreDto(
        String name,
        Set<BookSummaryDto> books
) {
}
