package org.example.springproject.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.springproject.config.MapperConfig;
import org.example.springproject.dto.book.BookDto;
import org.example.springproject.dto.book.BookDtoWithoutCategoryIds;
import org.example.springproject.dto.book.CreateBookRequestDto;
import org.example.springproject.model.Book;
import org.example.springproject.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book dtoToBook(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            Set<Long> categories = book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            bookDto.setCategoryIds(categories);
        }
    }
}
