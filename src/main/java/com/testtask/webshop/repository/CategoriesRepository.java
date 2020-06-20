package com.testtask.webshop.repository;

import java.util.Optional;
import com.testtask.webshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
