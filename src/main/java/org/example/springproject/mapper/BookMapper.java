package org.example.springproject.mapper;

import org.example.springproject.config.MapperConfig;
import org.example.springproject.dto.BookDto;
import org.example.springproject.dto.CreateBookRequestDto;
import org.example.springproject.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    Book dtoToBook(CreateBookRequestDto requestDto);
}
