package com.testtask.webshop.service;

import java.util.List;
import com.testtask.webshop.model.Discount;

public interface DiscounService {
    Discount create(Discount discount);

    Discount getById(Long id);

    Discount getByValue(Long value);

    List<Discount> getAll();
}
