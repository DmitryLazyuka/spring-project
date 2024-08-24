package org.example.springproject.service;

import java.util.List;
import org.example.springproject.dto.category.CategoryDto;
import org.example.springproject.dto.category.CreateCategoryRequestDto;
import org.example.springproject.model.Category;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    Category getCategoryById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);
}
