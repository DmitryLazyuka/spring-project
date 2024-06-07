package org.example.springproject.repository.book.spec;

import org.example.springproject.model.Book;
import org.example.springproject.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FromPriceSpecificationProvider implements SpecificationProvider<Book> {

    public static final String FROM_PRICE = "fromPrice";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.greaterThan(root.get("price"), params[0]);
    }

    @Override
    public String getKey() {
        return FROM_PRICE;
    }
}
