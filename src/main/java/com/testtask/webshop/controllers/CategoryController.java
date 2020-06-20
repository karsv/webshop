package com.testtask.webshop.controllers;

import java.util.List;
import java.util.stream.Collectors;
import com.testtask.webshop.dto.CategoryRequestDto;
import com.testtask.webshop.dto.CategoryResponseDto;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDto> getCategories() {
        return categoryService.getAll().stream()
                .map(c -> convertCategoryToCategoryResponseDto(c))
                .collect(Collectors.toList());
    }

    @PostMapping
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        return convertCategoryToCategoryResponseDto(categoryService.create(category));
    }

    private CategoryResponseDto convertCategoryToCategoryResponseDto(Category c) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(c.getId());
        categoryResponseDto.setName(c.getName());
        return categoryResponseDto;
    }
}
