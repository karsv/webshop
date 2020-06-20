package com.testtask.webshop.repository;

import java.util.Optional;
import com.testtask.webshop.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepositories extends JpaRepository<Discount, Long> {
    Optional<Discount> findByValue(Long value);
}
