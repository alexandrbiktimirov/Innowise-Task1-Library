package com.example.authorservice.controller;

import com.example.authorservice.dto.AuthorCreateDto;
import com.example.authorservice.dto.AuthorDto;
import com.example.authorservice.dto.AuthorUpdateDto;
import com.example.authorservice.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

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
