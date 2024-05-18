package org.example.springproject.service;

import java.util.List;
import org.example.springproject.dto.BookDto;
import org.example.springproject.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
