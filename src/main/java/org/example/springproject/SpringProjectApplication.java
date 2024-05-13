package org.example.springproject;

import java.math.BigDecimal;
import org.example.springproject.model.Book;
import org.example.springproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringProjectApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setIsbn("978-0-385-12167-5");
                book.setTitle("The Shining");
                book.setAuthor("Stephen King");
                book.setPrice(BigDecimal.valueOf(45.98));
                book.setDescription("horror novel by American author Stephen King");
                book.setCoverImage("https://en.wikipedia.org/wiki/File:The_Shining_(1977)_front_cover,_first_edition.jpg");

                bookService.save(book);
                System.out.println(bookService.findAll());
            }
        };
    }
}

