package com.example.authorservice.service;

import com.example.authorservice.dto.AuthorCreateDto;
import com.example.authorservice.dto.AuthorDto;
import com.example.authorservice.dto.AuthorUpdateDto;
import com.example.authorservice.exception.AuthorDoesNotExistException;
import com.example.authorservice.mapper.AuthorMapper;
import com.example.authorservice.model.Author;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper.INSTANCE::toDto)
                .toList();
    }

    public AuthorDto getAuthorById(long id) {
        return authorRepository.findById(id)
                .map(AuthorMapper.INSTANCE::toDto)
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist"));
    }

    @Transactional
    public void createAuthor(AuthorCreateDto authorCreateDto) {
        Author author = AuthorMapper.INSTANCE.toEntity(authorCreateDto);

        authorRepository.save(author);
    }

    @Transactional
    public void updateAuthor(long id, AuthorUpdateDto authorUpdateDto) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist"));

        if (authorUpdateDto.firstName() != null) {
            author.setFirstName(authorUpdateDto.firstName());
        }
        if (authorUpdateDto.lastName() != null) {
            author.setLastName(authorUpdateDto.lastName());
        }

        authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(long id) {
        authorRepository.delete(authorRepository.findById(id).orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist")));
    }
}
