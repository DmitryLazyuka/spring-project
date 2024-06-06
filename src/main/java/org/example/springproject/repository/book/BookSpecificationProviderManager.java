package org.example.springproject.repository.book;

import java.util.List;
import lombok.AllArgsConstructor;
import org.example.springproject.model.Book;
import org.example.springproject.repository.SpecificationProvider;
import org.example.springproject.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private List<SpecificationProvider<Book>> specificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return specificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find correct specification provider for key " + key));
    }
}
