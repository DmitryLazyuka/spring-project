package org.example.springproject.repository;

import java.util.List;
import org.example.springproject.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}

