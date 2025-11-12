package com.example.library.controller;

import com.example.library.dto.book.BookCreateDto;
import com.example.library.dto.book.BookDto;
import com.example.library.dto.book.BookUpdateDto;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public void addBook(@RequestBody BookCreateDto dto) {
        bookService.createBook(dto);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable long id, @RequestBody BookUpdateDto dto) {
        bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
}