package com.example.library.service;

import com.example.library.dto.author.AuthorCreateDto;
import com.example.library.dto.author.AuthorDto;
import com.example.library.dto.author.AuthorUpdateDto;
import com.example.library.exception.AuthorDoesNotExistException;
import com.example.library.mapper.AuthorMapper;
import com.example.library.model.Author;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public AuthorDto getAuthorById(long id) {
        return authorRepository.findById(id)
                .map(AuthorMapper.INSTANCE::toDto)
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist"));
    }

    @Transactional
    @Override
    public void createAuthor(AuthorCreateDto authorCreateDto) {
        Author author = AuthorMapper.INSTANCE.toEntity(authorCreateDto);

        authorRepository.save(author);
    }

    @Transactional
    @Override
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
    @Override
    public void deleteAuthor(long id) {
        authorRepository.delete(authorRepository.findById(id).orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist")));
    }
}
