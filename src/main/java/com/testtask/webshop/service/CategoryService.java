package com.testtask.webshop.service;

import java.util.List;
import com.testtask.webshop.model.Category;

public interface CategoryService {
    Category create(Category category);

    Category getByName(String name);

    List<Category> getAll();
}
