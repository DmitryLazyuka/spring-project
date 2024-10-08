package org.example.springproject.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.book.BookDto;
import org.example.springproject.dto.book.BookDtoWithoutCategoryIds;
import org.example.springproject.dto.book.BookSearchParametersDto;
import org.example.springproject.dto.book.CreateBookRequestDto;
import org.example.springproject.exception.EntityNotFoundException;
import org.example.springproject.mapper.BookMapper;
import org.example.springproject.model.Book;
import org.example.springproject.model.Category;
import org.example.springproject.repository.book.BookRepository;
import org.example.springproject.repository.book.BookSpecificationBuilder;
import org.example.springproject.repository.category.CategoryRepository;
import org.example.springproject.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.dtoToBook(requestDto);
        book.setCategories(categoriesIdToCategories(requestDto.getCategoryIds()));
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no book with id " + id));
        return bookMapper.toBookDto(book);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        bookMapper.updateBookFromDto(requestDto, book);
        book.setCategories(categoriesIdToCategories(requestDto.getCategoryIds()));
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto bookSearchParametersDto,
                                Pageable pageable) {
        Specification<Book> bookSpecification =
                bookSpecificationBuilder.build(bookSearchParametersDto);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toBookDto).toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategories_Id(categoryId).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private Set<Category> categoriesIdToCategories(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(categoryRepository::getReferenceById)
                .collect(Collectors.toSet());
    }
}
