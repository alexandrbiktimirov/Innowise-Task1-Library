package com.example.library.service;

import com.example.library.dto.book.BookCreateDto;
import com.example.library.dto.book.BookDto;
import com.example.library.dto.book.BookUpdateDto;
import com.example.library.exception.AuthorDoesNotExistException;
import com.example.library.exception.BookDoesNotExistException;
import com.example.library.exception.GenreDoesNotExistException;
import com.example.library.mapper.LibraryMapper;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.GenreRepository;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final LibraryMapper libraryMapper;

    public List<BookDto> getAllBooks() {
        return libraryMapper.toBookDtos(bookRepository.findAll());
    }

    public BookDto getBookById(long id) {
        return bookRepository.findById(id)
                .map(libraryMapper::toBookDto)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id " + id + " does not exist"));
    }

    @Transactional
    public void createBook(BookCreateDto dto) {
        Book book = libraryMapper.toBook(dto);
        book.setAuthors(fetchAuthors(dto.authorIds()));
        book.setGenres(fetchGenres(dto.genreIds()));

        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(long id, BookUpdateDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id " + id + " does not exist"));

        libraryMapper.updateBookFromDto(dto, book);

        if (dto.authorIds() != null) {
            book.setAuthors(fetchAuthors(dto.authorIds()));
        }
        if (dto.genreIds() != null) {
            book.setGenres(fetchGenres(dto.genreIds()));
        }

        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id " + id + " does not exist"));
        bookRepository.delete(book);
    }

    private Set<Author> fetchAuthors(Set<Long> authorIds) {
        List<Author> authors = authorRepository.findAllById(authorIds);
        if (authors.size() != authorIds.size()) {
            Set<Long> foundIds = authors.stream().map(Author::getId).collect(Collectors.toSet());
            Set<Long> missing = new HashSet<>(authorIds);
            missing.removeAll(foundIds);
            throw new AuthorDoesNotExistException("Author with id(s) " + missing + " do not exist");
        }
        return new HashSet<>(authors);
    }

    private Set<Genre> fetchGenres(Set<Long> genreIds) {
        List<Genre> genres = genreRepository.findAllById(genreIds);
        if (genres.size() != genreIds.size()) {
            Set<Long> foundIds = genres.stream().map(Genre::getId).collect(Collectors.toSet());
            Set<Long> missing = new HashSet<>(genreIds);
            missing.removeAll(foundIds);
            throw new GenreDoesNotExistException("Genre with id(s) " + missing + " do not exist");
        }
        return new HashSet<>(genres);
    }
}
