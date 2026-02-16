package com.example.genreservice.service;

import com.example.library.dto.genre.GenreCreateDto;
import com.example.library.dto.genre.GenreDto;
import com.example.library.dto.genre.GenreUpdateDto;
import com.example.library.exception.GenreDoesNotExistException;
import com.example.library.mapper.GenreMapper;
import com.example.library.model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(GenreMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public GenreDto getGenreById(long id) {
        return genreRepository.findById(id)
                .map(GenreMapper.INSTANCE::toDto)
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id " + id + " does not exist"));
    }

    @Transactional
    @Override
    public void createGenre(GenreCreateDto genreCreateDto) {
        Genre genre = GenreMapper.INSTANCE.toEntity(genreCreateDto);
        genreRepository.save(genre);
    }

    @Transactional
    @Override
    public void updateGenre(long id, GenreUpdateDto genreUpdateDto) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new GenreDoesNotExistException("Genre with id " + id + " does not exist"));

        genre.setName(genreUpdateDto.name());
        genreRepository.save(genre);
    }

    @Transactional
    @Override
    public void deleteGenre(long id) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id " + id + " does not exist"));
        genreRepository.delete(genre);
    }
}