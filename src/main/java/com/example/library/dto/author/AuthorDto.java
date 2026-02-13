package com.example.library.dto.author;

import com.example.library.dto.book.BookSummaryDto;

import java.util.Set;

public record AuthorDto(
        String firstName,
        String lastName,
        Set<BookSummaryDto> books
) {
}
