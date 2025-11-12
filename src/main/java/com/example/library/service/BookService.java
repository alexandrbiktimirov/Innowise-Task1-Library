package com.example.library.service;

import com.example.library.dto.book.BookCreateDto;
import com.example.library.dto.book.BookDto;
import com.example.library.dto.book.BookUpdateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(long id);

    @Transactional
    void createBook(BookCreateDto bookCreateDto);

    @Transactional
    void updateBook(long id, BookUpdateDto bookUpdateDto);

    @Transactional
    void deleteBook(long id);
}