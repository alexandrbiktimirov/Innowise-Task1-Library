package com.example.library.service;

import com.example.library.dto.genre.GenreCreateDto;
import com.example.library.dto.genre.GenreDto;
import com.example.library.dto.genre.GenreUpdateDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAllGenres();
    GenreDto getGenreById(long id);

    @Transactional
    void createGenre(GenreCreateDto genreCreateDto);

    @Transactional
    void updateGenre(long id, GenreUpdateDto genreUpdateDto);

    @Transactional
    void deleteGenre(long id);
}