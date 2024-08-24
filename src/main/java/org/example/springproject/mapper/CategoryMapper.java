package org.example.springproject.mapper;

import org.example.springproject.config.MapperConfig;
import org.example.springproject.dto.category.CategoryDto;
import org.example.springproject.dto.category.CreateCategoryRequestDto;
import org.example.springproject.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto requestDto);
}
