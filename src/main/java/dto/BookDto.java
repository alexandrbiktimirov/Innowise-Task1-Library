package dto;

import java.util.Set;
import java.util.stream.Collectors;

public record BookDto(Long id, String title, String description, Set<AuthorDto> authors, Set<GenreDto> genres) {
    @Override
    public String toString() {
        String authorNames = authors.stream()
                .map(authorDto -> authorDto.firstName() + " " + authorDto.lastName())
                .collect(Collectors.joining(", "));

        String genreNames = genres.stream()
                .map(GenreDto::name)
                .collect(Collectors.joining(", "));

        return "Id: " + id + ", Title: " + title + ", Authors: [" + authorNames + "], Description: " + description + ". Genres: [" + genreNames + "]";
    }
}
