package com.correios.catalog.repositories;

import com.correios.catalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findOneByTitle(String name);

    Optional<Category> findOneById(Long id);
}
