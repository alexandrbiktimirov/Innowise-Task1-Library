package com.example.library.service;

import com.example.library.dto.author.AuthorCreateDto;
import com.example.library.dto.author.AuthorDto;
import com.example.library.dto.author.AuthorUpdateDto;
import com.example.library.exception.AuthorDoesNotExistException;
import com.example.library.exception.BookDoesNotExistException;
import com.example.library.mapper.LibraryMapper;
import com.example.library.model.Author;
import com.example.library.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService  {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final LibraryMapper libraryMapper;

    public List<AuthorDto> getAllAuthors() {
        return libraryMapper.toAuthorDtos(authorRepository.findAll());
    }

    public AuthorDto getAuthorById(long id) {
        return authorRepository.findById(id)
                .map(libraryMapper::toAuthorDto)
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist"));
    }

    @Transactional
    public void createAuthor(AuthorCreateDto authorCreateDto) {
        Author author = libraryMapper.toAuthor(authorCreateDto);
        author.setBooks(fetchBooks(authorCreateDto.bookIds()));

        authorRepository.save(author);
    }

    @Transactional
    public void updateAuthor(long id, AuthorUpdateDto authorUpdateDto) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist"));

        libraryMapper.updateAuthorFromDto(authorUpdateDto, author);
        if (authorUpdateDto.bookIds() != null) {
            author.setBooks(fetchBooks(authorUpdateDto.bookIds()));
        }

        authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(long id) {
        authorRepository.delete(authorRepository.findById(id).orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist")));
    }

    private Set<Book> fetchBooks(Set<Long> bookIds) {
        List<Book> books = bookRepository.findAllById(bookIds);
        if (books.size() != bookIds.size()) {
            Set<Long> foundIds = books.stream().map(Book::getId).collect(Collectors.toSet());
            Set<Long> missing = new HashSet<>(bookIds);
            missing.removeAll(foundIds);
            throw new BookDoesNotExistException("Book with id(s) " + missing + " do not exist");
        }
        return new HashSet<>(books);
    }
}
