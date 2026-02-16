package com.example.bookservice.controller;

import com.example.library.dto.book.BookCreateDto;
import com.example.library.dto.book.BookDto;
import com.example.library.dto.book.BookUpdateDto;
import com.example.library.dto.book.DownloadedImage;
import com.example.library.service.BookImageService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.BookService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;
    private final BookImageService bookImageService;

    public BookController(BookService bookService, BookImageService bookImageService) {
        this.bookService = bookService;
        this.bookImageService = bookImageService;
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

    @GetMapping("/{id}/image")
    public ResponseEntity<StreamingResponseBody> getBookImage(@PathVariable long id) {
        DownloadedImage img = bookImageService.downloadImage(id);

        ContentDisposition contentDisposition = ContentDisposition
                .attachment()
                .filename(img.filename(), StandardCharsets.UTF_8)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.parseMediaType(img.contentType()));

        if (img.length() > 0){
            headers.setContentLength(img.length());
        }

        StreamingResponseBody body = outputStream -> {
            try (InputStream in = img.inputStream()) {
                in.transferTo(outputStream);
            }
        };

        return ResponseEntity.ok().headers(headers).body(body);
    }

    @PostMapping(
            path = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> uploadBookImage(@PathVariable long id, @RequestParam("file") MultipartFile file) throws IOException {
        bookImageService.uploadImage(id, file.getInputStream(), file.getOriginalFilename(), file.getContentType());

        return ResponseEntity.noContent().build();
    }
}