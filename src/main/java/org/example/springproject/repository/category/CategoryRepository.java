package org.example.springproject.repository.category;

import java.util.Set;
import org.example.springproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT COUNT(c) FROM Category c WHERE c.id IN :ids")
    long countByIds(@Param("ids") Set<Long> ids);
}
