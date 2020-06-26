package com.testtask.webshop.service.impl;

import java.util.List;
import java.util.Optional;
import com.testtask.webshop.exceptions.CategoryServiceException;
import com.testtask.webshop.exceptions.ProductServiceException;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.repository.CategoriesRepository;
import com.testtask.webshop.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoriesRepository categoriesRepository;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    @Transactional
    public Category create(Category category) {
        if(categoriesRepository.findByName(category.getName()).isPresent()){
            throw new CategoryServiceException("There is category with such name!");
        }
        return categoriesRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getByName(String name) {
        Optional<Category> optionalCategory = categoriesRepository.findByName(name);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new CategoryServiceException("No such category!");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return categoriesRepository.findAll();
    }
}
