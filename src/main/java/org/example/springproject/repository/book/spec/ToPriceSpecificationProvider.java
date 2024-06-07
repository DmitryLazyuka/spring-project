package org.example.springproject.repository.book.spec;

import org.example.springproject.model.Book;
import org.example.springproject.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ToPriceSpecificationProvider implements SpecificationProvider<Book> {

    public static final String TO_PRICE = "toPrice";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.lessThan(root.get("price"), params[0]);
    }

    @Override
    public String getKey() {
        return TO_PRICE;
    }
}
