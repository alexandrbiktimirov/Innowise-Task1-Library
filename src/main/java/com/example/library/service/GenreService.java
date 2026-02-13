package com.example.library.service;

import com.example.library.dto.genre.GenreCreateDto;
import com.example.library.dto.genre.GenreDto;
import com.example.library.dto.genre.GenreUpdateDto;
import com.example.library.exception.BookDoesNotExistException;
import com.example.library.exception.GenreDoesNotExistException;
import com.example.library.mapper.LibraryMapper;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.repository.BookRepository;
import com.example.library.repository.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final LibraryMapper libraryMapper;

    public List<GenreDto> getAllGenres() {
        return libraryMapper.toGenreDtos(genreRepository.findAll());
    }

    public GenreDto getGenreById(long id) {
        return genreRepository.findById(id)
                .map(libraryMapper::toGenreDto)
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id " + id + " does not exist"));
    }

    @Transactional
    public void createGenre(GenreCreateDto genreCreateDto) {
        Genre genre = libraryMapper.toGenre(genreCreateDto);
        genre.setBooks(fetchBooks(genreCreateDto.bookIds()));
        genreRepository.save(genre);
    }

    @Transactional
    public void updateGenre(long id, GenreUpdateDto genreUpdateDto) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new GenreDoesNotExistException("Genre with id " + id + " does not exist"));

        libraryMapper.updateGenreFromDto(genreUpdateDto, genre);
        if (genreUpdateDto.bookIds() != null) {
            genre.setBooks(fetchBooks(genreUpdateDto.bookIds()));
        }
        genreRepository.save(genre);
    }

    @Transactional
    public void deleteGenre(long id) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id " + id + " does not exist"));
        genreRepository.delete(genre);
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
