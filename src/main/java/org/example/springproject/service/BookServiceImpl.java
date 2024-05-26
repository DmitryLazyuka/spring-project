package org.example.springproject.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.BookDto;
import org.example.springproject.dto.CreateBookRequestDto;
import org.example.springproject.exception.EntityNotFoundException;
import org.example.springproject.mapper.BookMapper;
import org.example.springproject.model.Book;
import org.example.springproject.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.dtoToBook(requestDto);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
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
        Book book = optionalBook.get();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setPrice(requestDto.getPrice());
        book.setCoverImage(requestDto.getCoverImage());
        book.setDescription(requestDto.getDescription());
        book.setIsbn(requestDto.getIsbn());

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toBookDto(updatedBook);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
