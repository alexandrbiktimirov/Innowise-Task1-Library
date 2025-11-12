package com.example.library.service;

import com.example.library.dto.book.BookCreateDto;
import com.example.library.dto.book.BookDto;
import com.example.library.dto.book.BookUpdateDto;
import com.example.library.exception.AuthorDoesNotExistException;
import com.example.library.exception.BookDoesNotExistException;
import com.example.library.exception.GenreDoesNotExistException;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.GenreRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(long id) {
        return bookRepository.findById(id)
                .map(BookMapper.INSTANCE::toDto)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id " + id + " does not exist"));
    }

    @Transactional
    @Override
    public void createBook(BookCreateDto dto) {
        Author author = authorRepository.findById(dto.authorId())
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + dto.authorId() + " does not exist"));
        Genre genre = genreRepository.findById(dto.genreId())
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id " + dto.genreId() + " does not exist"));

        Book book = BookMapper.INSTANCE.toEntity(dto);
        book.setAuthor(author);
        book.setGenre(genre);

        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBook(long id, BookUpdateDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id " + id + " does not exist"));

        book.setTitle(dto.title());
        book.setDescription(dto.description());

        Author author = authorRepository.findById(dto.authorId()).orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + dto.authorId() + " does not exist"));
        book.setAuthor(author);
        Genre genre = genreRepository.findById(dto.genreId()).orElseThrow(() -> new GenreDoesNotExistException("Genre with id " + dto.genreId() + " does not exist"));
        book.setGenre(genre);

        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id " + id + " does not exist"));
        bookRepository.delete(book);
    }
}