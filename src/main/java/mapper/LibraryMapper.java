package mapper;

import dto.AuthorDto;
import dto.BookDto;
import dto.GenreDto;
import model.Author;
import model.Book;
import model.Genre;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    AuthorDto toAuthorDto(Author author);

    List<AuthorDto> toAuthorDtos(List<Author> authors);

    Set<AuthorDto> toAuthorDtoSet(Set<Author> authors);

    GenreDto toGenreDto(Genre genre);

    List<GenreDto> toGenreDtos(List<Genre> genres);

    Set<GenreDto> toGenreDtoSet(Set<Genre> genres);

    BookDto toBookDto(Book book);

    List<BookDto> toBookDtos(List<Book> books);
}
