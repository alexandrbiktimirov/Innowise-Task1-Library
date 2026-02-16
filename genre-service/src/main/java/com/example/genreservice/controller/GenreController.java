package com.example.genreservice.controller;

import com.example.library.dto.genre.GenreCreateDto;
import com.example.library.dto.genre.GenreDto;
import com.example.library.dto.genre.GenreUpdateDto;
import org.springframework.web.bind.annotation.*;
import com.example.library.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDto> getGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable long id) {
        return genreService.getGenreById(id);
    }

    @PostMapping
    public void addGenre(@RequestBody GenreCreateDto dto) {
        genreService.createGenre(dto);
    }

    @PutMapping("/{id}")
    public void updateGenre(@PathVariable long id, @RequestBody GenreUpdateDto dto) {
        genreService.updateGenre(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable long id) {
        genreService.deleteGenre(id);
    }
}