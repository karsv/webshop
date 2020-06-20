package com.testtask.webshop.repository;

import java.util.List;
import java.util.Optional;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getByName(String name);

    List<Product> getAllByCategory(Category category);
}
