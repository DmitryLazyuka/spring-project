package org.example.springproject.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.category.CategoryDto;
import org.example.springproject.dto.category.CreateCategoryRequestDto;
import org.example.springproject.mapper.CategoryMapper;
import org.example.springproject.model.Category;
import org.example.springproject.repository.category.CategoryRepository;
import org.example.springproject.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "There is no category with id " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "There is no category with id " + id));
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        Optional<Category> foundCategory = categoryRepository.findById(id);

        if (foundCategory.isEmpty()) {
            throw new NoSuchElementException("There is no category with id " + id);
        }
        Category category = foundCategory.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
