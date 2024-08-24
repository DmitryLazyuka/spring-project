package org.example.springproject.service.impl;

import java.util.List;
import java.util.Optional;
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
        if (!hasValidCategories(requestDto)) {
            throw new EntityNotFoundException("Some categories are missing in DB");
        }
        Book book = bookMapper.dtoToBook(requestDto);
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
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

        if (!hasValidCategories(requestDto)) {
            throw new EntityNotFoundException("Some listed categories are missing in DB");
        }

        Book book = optionalBook.get();
        book.setId(id);
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setPrice(requestDto.getPrice());
        book.setCoverImage(requestDto.getCoverImage());
        book.setDescription(requestDto.getDescription());
        book.setIsbn(requestDto.getIsbn());
        book.setCategories(requestDto.getCategoryIds()
                .stream().map(Category::new).collect(Collectors.toSet()));

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toBookDto(updatedBook);
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
                .collect(Collectors.toList());
    }

    private boolean hasValidCategories(CreateBookRequestDto requestDto) {
        Set<Long> categoryIds = requestDto.getCategoryIds();
        return categoryIds.size() == categoryRepository.countByIds(categoryIds);
    }
}
