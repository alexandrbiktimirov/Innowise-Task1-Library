package com.example.genreservice.mapper;

import com.example.library.dto.genre.GenreCreateDto;
import com.example.library.dto.genre.GenreDto;
import com.example.library.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenreMapper {
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    Genre toEntity(GenreCreateDto genreDto);
    GenreDto toDto(Genre genre);
}
