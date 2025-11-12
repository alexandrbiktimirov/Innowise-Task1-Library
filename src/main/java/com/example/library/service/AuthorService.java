package com.example.library.service;

import com.example.library.dto.author.AuthorCreateDto;
import com.example.library.dto.author.AuthorDto;
import com.example.library.dto.author.AuthorUpdateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(long id);

    @Transactional
    void createAuthor(AuthorCreateDto authorCreateDto);

    @Transactional
    void updateAuthor(long id, AuthorUpdateDto authorUpdateDto);

    @Transactional
    void deleteAuthor(long id);
}
