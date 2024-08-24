package org.example.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.book.BookDtoWithoutCategoryIds;
import org.example.springproject.dto.category.CategoryDto;
import org.example.springproject.dto.category.CreateCategoryRequestDto;
import org.example.springproject.service.BookService;
import org.example.springproject.service.CategoryService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category controller", description = "Endpoint for operate with Categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(summary = "Create new category")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryRequestDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @Operation(summary = "Retrieve all books by category")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable @Positive Long id) {
        return bookService.findByCategoryId(id);
    }

    @GetMapping
    @Operation(summary = "Retrieve all categories")
    @PreAuthorize("hasRole('USER')")
    public List<CategoryDto> getAll(@ParameterObject @PageableDefault Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @Operation(summary = "Update category details")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable @Positive Long id,
                                      @RequestBody @Valid CreateCategoryRequestDto categoryDto) {
        return categoryService.update(id,categoryDto);
    }

    @Operation(summary = "Remove category")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable @Positive Long id) {
        categoryService.deleteById(id);
    }
}
