package org.example.springproject.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.springproject.model.Book;
import org.example.springproject.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
