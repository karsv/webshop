package com.testtask.webshop.service.impl;

import java.util.List;
import java.util.Optional;
import com.testtask.webshop.exceptions.CategoryServiceException;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.repository.CategoriesRepository;
import com.testtask.webshop.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoriesRepository categoriesRepository;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    public Category create(Category category) {
        return categoriesRepository.save(category);
    }

    @Override
    public Category getByName(String name) {
        Optional<Category> optionalCategory = categoriesRepository.findByName(name);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new CategoryServiceException("No such category!");
    }

    @Override
    public List<Category> getAll() {
        return categoriesRepository.findAll();
    }
}
