package com.example.library.controller;

import com.example.library.dto.author.AuthorCreateDto;
import com.example.library.dto.author.AuthorDto;
import com.example.library.dto.author.AuthorUpdateDto;
import com.example.library.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> getAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public void addAuthor(@RequestBody AuthorCreateDto authorCreateDto) {
        authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable long id, @RequestBody AuthorUpdateDto authorUpdateDto) {
        authorService.updateAuthor(id, authorUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable long id) {
        authorService.deleteAuthor(id);
    }
}
