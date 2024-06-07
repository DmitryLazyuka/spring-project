package org.example.springproject.repository.book.spec;

import java.util.Arrays;
import org.example.springproject.model.Book;
import org.example.springproject.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    public static final String TITLE = "title";

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get(TITLE).in(Arrays.stream(params).toArray());
    }

    @Override
    public String getKey() {
        return TITLE;
    }
}
