package com.example.library.mapper;

import com.example.library.dto.author.AuthorDto;
import com.example.library.dto.author.AuthorCreateDto;
import com.example.library.dto.author.AuthorSummaryDto;
import com.example.library.dto.author.AuthorUpdateDto;
import com.example.library.dto.book.BookDto;
import com.example.library.dto.book.BookCreateDto;
import com.example.library.dto.book.BookSummaryDto;
import com.example.library.dto.book.BookUpdateDto;
import com.example.library.dto.genre.GenreDto;
import com.example.library.dto.genre.GenreCreateDto;
import com.example.library.dto.genre.GenreSummaryDto;
import com.example.library.dto.genre.GenreUpdateDto;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    @Mapping(target = "books", expression = "java(mapBookSummarySet(author.getBooks()))")
    AuthorDto toAuthorDto(Author author);

    List<AuthorDto> toAuthorDtos(List<Author> authors);

    Set<AuthorDto> toAuthorDtoSet(Set<Author> authors);

    AuthorSummaryDto toAuthorSummaryDto(Author author);

    @Mapping(target = "books", ignore = true)
    Author toAuthor(AuthorCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "books", ignore = true)
    void updateAuthorFromDto(AuthorUpdateDto dto, @MappingTarget Author author);

    @Mapping(target = "books", expression = "java(mapBookSummarySet(genre.getBooks()))")
    GenreDto toGenreDto(Genre genre);

    List<GenreDto> toGenreDtos(List<Genre> genres);

    Set<GenreDto> toGenreDtoSet(Set<Genre> genres);

    GenreSummaryDto toGenreSummaryDto(Genre genre);

    @Mapping(target = "books", ignore = true)
    Genre toGenre(GenreCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "books", ignore = true)
    void updateGenreFromDto(GenreUpdateDto dto, @MappingTarget Genre genre);

    @Mapping(target = "authors", expression = "java(mapAuthorSummarySet(book.getAuthors()))")
    @Mapping(target = "genres", expression = "java(mapGenreSummarySet(book.getGenres()))")
    BookDto toBookDto(Book book);

    List<BookDto> toBookDtos(List<Book> books);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Book toBook(BookCreateDto dto);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    void updateBookFromDto(BookUpdateDto dto, @MappingTarget Book book);

    BookSummaryDto toBookSummaryDto(Book book);

    default Set<BookSummaryDto> mapBookSummarySet(Set<Book> books) {
        if (books == null) {
            return null;
        }
        return books.stream().map(this::toBookSummaryDto).collect(java.util.stream.Collectors.toSet());
    }

    default Set<AuthorSummaryDto> mapAuthorSummarySet(Set<Author> authors) {
        if (authors == null) {
            return null;
        }
        return authors.stream().map(this::toAuthorSummaryDto).collect(java.util.stream.Collectors.toSet());
    }

    default Set<GenreSummaryDto> mapGenreSummarySet(Set<Genre> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream().map(this::toGenreSummaryDto).collect(java.util.stream.Collectors.toSet());
    }
}
