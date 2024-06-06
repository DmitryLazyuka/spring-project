package org.example.springproject.repository.book;

import lombok.AllArgsConstructor;
import org.example.springproject.dto.BookSearchParametersDto;
import org.example.springproject.model.Book;
import org.example.springproject.repository.SpecificationBuilder;
import org.example.springproject.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        if (bookSearchParametersDto.getAuthors() != null
                && bookSearchParametersDto.getAuthors().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider(
                    "author").getSpecification(bookSearchParametersDto.getAuthors()));
        }
        if (bookSearchParametersDto.getTitles() != null
                && bookSearchParametersDto.getTitles().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider(
                    "title").getSpecification(bookSearchParametersDto.getTitles()));
        }
        if (bookSearchParametersDto.getFromPrice() != null) {
            String[] fromParameter = {String.valueOf(bookSearchParametersDto.getFromPrice())};
            spec = spec.and(specificationProviderManager.getSpecificationProvider(
                    "fromPrice").getSpecification(fromParameter));
        }
        if (bookSearchParametersDto.getToPrice() != null) {
            String[] toParameter = {String.valueOf(bookSearchParametersDto.getToPrice())};
            spec = spec.and(specificationProviderManager.getSpecificationProvider(
                    "toPrice").getSpecification(toParameter));
        }
        return spec;
    }
}
