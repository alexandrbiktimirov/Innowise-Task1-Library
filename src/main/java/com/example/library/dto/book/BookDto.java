package com.example.library.dto.book;

import com.example.library.dto.author.AuthorSummaryDto;
import com.example.library.dto.genre.GenreSummaryDto;

import java.util.Set;

public record BookDto(
        String title,
        String description,
        Set<AuthorSummaryDto> authors,
        Set<GenreSummaryDto> genres
) {
}
