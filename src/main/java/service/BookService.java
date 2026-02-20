package service;

import dto.BookDto;
import exception.AuthorDoesNotExistException;
import exception.BookDoesNotExistException;
import exception.GenreDoesNotExistException;
import exception.InvalidIdFormatException;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import mapper.LibraryMapper;
import model.Author;
import model.Book;
import model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AuthorDao;
import repository.BookDao;
import repository.GenreDao;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final Messages messages;
    private final LibraryMapper libraryMapper;

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return libraryMapper.toBookDtos(bookDao.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<BookDto> getBookById(OptionalLong bookId) {
        if (bookId.isEmpty()) throw new InvalidIdFormatException(messages.get("common.invalid.format"));

        var id = bookId.getAsLong();
        Book book = bookDao.findById(id);

        if (book == null) throw new BookDoesNotExistException(messages.get("book.notfound", id));

        return Optional.of(libraryMapper.toBookDto(book));
    }

    @Transactional
    public void createBook(String title, String description, Set<Long> authorIds, Set<Long> genreIds) {
        Book book = new Book(title, description);

        book.setAuthors(resolveAuthors(authorIds));
        book.setGenres(resolveGenres(genreIds));

        bookDao.create(book);
    }

    @Transactional
    public void updateBook(long id, String title, String description, Set<Long> authorIds, Set<Long> genreIds) {
        Book book = bookDao.findById(id);
        if (book == null) {
            throw new BookDoesNotExistException(messages.get("book.notfound", id));
        }

        book.setTitle(title);
        book.setDescription(description);
        book.setAuthors(resolveAuthors(authorIds));
        book.setGenres(resolveGenres(genreIds));

        bookDao.update(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (bookDao.findById(id) == null) {
            throw new BookDoesNotExistException(messages.get("book.notfound", id));
        }

        bookDao.delete(id);
    }

    private Set<Author> resolveAuthors(Set<Long> authorIds) {
        return authorIds.stream()
                .map(authorId -> {
                    Author author = authorDao.findById(authorId);
                    if (author == null) {
                        throw new AuthorDoesNotExistException(messages.get("author.notfound", authorId));
                    }
                    return author;
                })
                .collect(Collectors.toSet());
    }

    private Set<Genre> resolveGenres(Set<Long> genreIds) {
        return genreIds.stream()
                .map(genreId -> {
                    Genre genre = genreDao.findById(genreId);
                    if (genre == null) {
                        throw new GenreDoesNotExistException(messages.get("genre.notfound", genreId));
                    }
                    return genre;
                })
                .collect(Collectors.toSet());
    }
}