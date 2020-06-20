package com.testtask.webshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.testtask.webshop.exceptions.CategoryServiceException;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.repository.CategoriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceImplTest {
    private static final String CATEGORY_NAME = "Category";
    private static final String FAKE_CATEGORY_NAME = "Chupa-Chups";
    private static final String CATEGORY_ONE = "One";
    private static final String CATEGORY_TWO = "Two";
    private static final String CATEGORY_THREE = "Three";
    private static final Long CATEGORY_ID = 1L;
    private static final Long CATEGORY_ONE_ID = 10l;
    private static final Long CATEGORY_TWO_ID = 20l;
    private static final Long CATEGORY_THREE_ID = 30l;

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category categoryWithoutId;
    private Category categoryWithId;
    private Category category1;
    private Category category2;
    private Category category3;
    List<Category> categoryList;

    @BeforeEach
    void setUp() {
        categoryWithoutId = new Category();
        categoryWithoutId.setName(CATEGORY_NAME);

        categoryWithId = new Category();
        categoryWithId.setId(CATEGORY_ID);
        categoryWithId.setName(CATEGORY_NAME);

        category1 = new Category();
        category1.setId(CATEGORY_ONE_ID);
        category1.setName(CATEGORY_ONE);

        category2 = new Category();
        category2.setId(CATEGORY_TWO_ID);
        category2.setName(CATEGORY_TWO);

        category3 = new Category();
        category3.setId(CATEGORY_THREE_ID);
        category3.setName(CATEGORY_THREE);

        categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);

        when(categoriesRepository.save(categoryWithoutId)).thenReturn(categoryWithId);
        when(categoriesRepository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(categoryWithId));
        when(categoriesRepository.findByName(FAKE_CATEGORY_NAME)).thenThrow(CategoryServiceException.class);
        when(categoriesRepository.findAll()).thenReturn(categoryList);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() {
        assertEquals(categoryWithId, categoryService.create(categoryWithoutId));
    }

    @Test
    void getByName() {
        assertEquals(categoryWithId, categoryService.getByName(CATEGORY_NAME));
        assertThrows(CategoryServiceException.class, () -> categoryService.getByName(FAKE_CATEGORY_NAME));
    }

    @Test
    void getAll() {
        assertEquals(categoryList, categoryService.getAll());
    }
}
