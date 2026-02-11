package service;

import dto.BookDto;
import exception.AuthorDoesNotExistException;
import exception.BookDoesNotExistException;
import exception.GenreDoesNotExistException;
import mapper.LibraryMapper;
import model.Author;
import model.Book;
import model.Genre;
import org.springframework.transaction.annotation.Transactional;
import repository.AuthorDao;
import repository.BookDao;
import repository.GenreDao;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final LibraryMapper libraryMapper;

    public BookService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, LibraryMapper libraryMapper) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.libraryMapper = libraryMapper;
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return libraryMapper.toBookDtos(bookDao.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<BookDto> getBookById(Long id) {
        Book book = bookDao.findById(id);

        if (book == null) {
            throw new BookDoesNotExistException(id);
        }

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
            throw new BookDoesNotExistException(id);
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
            throw new BookDoesNotExistException(id);
        }

        bookDao.delete(id);
    }

    private Set<Author> resolveAuthors(Set<Long> authorIds) {
        return authorIds.stream()
                .map(authorId -> {
                    Author author = authorDao.findById(authorId);
                    if (author == null) {
                        throw new AuthorDoesNotExistException(authorId);
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
                        throw new GenreDoesNotExistException(genreId);
                    }
                    return genre;
                })
                .collect(Collectors.toSet());
    }
}