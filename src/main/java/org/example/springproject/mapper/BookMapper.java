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

    Book dtoToBook(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Mapping(target = "id", ignore = true)
    void updateBookFromDto(CreateBookRequestDto requestDto,
                               @MappingTarget Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            Set<Long> categories = book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            bookDto.setCategoryIds(categories);
        }
    }

    @AfterMapping
    default void mapCategoryIdsToCategories(@MappingTarget Book book,
                                            CreateBookRequestDto requestDto) {
        Set<Category> categories = requestDto.getCategoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }
}
