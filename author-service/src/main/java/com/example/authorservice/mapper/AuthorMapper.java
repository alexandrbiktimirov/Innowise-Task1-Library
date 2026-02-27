package com.example.authorservice.mapper;

import com.example.authorservice.dto.AuthorCreateDto;
import com.example.authorservice.dto.AuthorDto;
import com.example.authorservice.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toEntity(AuthorCreateDto authorDto);
    AuthorDto toDto(Author author);
}
