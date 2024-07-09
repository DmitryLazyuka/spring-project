package org.example.springproject.service;

import java.util.List;
import org.example.springproject.dto.book.BookDto;
import org.example.springproject.dto.book.BookSearchParametersDto;
import org.example.springproject.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto bookSearchParametersDto,
                         Pageable pageable);
}
