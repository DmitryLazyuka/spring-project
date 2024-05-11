package org.example.springproject.service;

import java.util.List;
import org.example.springproject.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}


