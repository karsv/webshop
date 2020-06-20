package com.testtask.webshop.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.testtask.webshop.exceptions.ProductServiceException;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.model.Discount;
import com.testtask.webshop.model.Product;
import com.testtask.webshop.repository.ProductRepository;
import com.testtask.webshop.service.CategoryService;
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
class ProductServiceImplTest {
    private static final String PRODUCT_NAME = "PRODUCT";
    private static final String PRODUCT_FAKE_NAME = "HALVA";
    private static final String PRODUCT_ONE = "ONE";
    private static final String PRODUCT_TWO = "TWO";
    private static final String PRODUCT_THREE = "THREE";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(100.0);

    private static final Long PRODUCT_ID = 1l;
    private static final Long PRODUCT_FAKE_ID = 323l;
    private static final Long PRODUCT_ONE_ID = 11L;
    private static final Long PRODUCT_TWO_ID = 22L;
    private static final Long PRODUCT_THREE_ID = 33L;

    private static final Long DISCOUNT_OLD_ID = 1L;
    private static final Long DISCOUNT_OLD_VALUE = 5L;
    private static final Long DISCOUNT_NEW_ID = 2L;
    private static final Long DISCOUNT_NEW_VALUE = 10L;

    private static final Long CATEGORY_ONE_ID = 1L;
    private static final String CATEGORY_ONE_NAME = "Category 1";
    private static final Long CATEGORY_TWO_ID = 2L;
    private static final String CATEGORY_TWO_NAME = "Category 2";

    private static final Long QUANTITY_OLD = 12L;
    private static final Long QUANTITY_NEW = 5L;
    private static final Long QUANTITY_FOR_PAY = 7L;
    private static final Long EXTRA_QUANTITY_FOR_PAY = 21L;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product productWithId;
    private Product productWithOutId;
    private Product productWithNewDiscount;
    private Product productWithNewQuantity;
    private Product product1;
    private Product product2;
    private Product product3;
    private Category category1;
    private Category category2;
    private Discount oldDiscount;
    private Discount newDiscount;
    private List<Product> allProductList;
    private List<Product> allProductListByCategory;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(CATEGORY_ONE_ID);
        category1.setName(CATEGORY_ONE_NAME);

        category2 = new Category();
        category2.setId(CATEGORY_TWO_ID);
        category2.setName(CATEGORY_TWO_NAME);

        oldDiscount = new Discount();
        oldDiscount.setId(DISCOUNT_OLD_ID);
        oldDiscount.setValue(DISCOUNT_OLD_VALUE);

        newDiscount = new Discount();
        newDiscount.setId(DISCOUNT_NEW_ID);
        newDiscount.setValue(DISCOUNT_NEW_VALUE);

        productWithOutId = new Product();
        productWithOutId.setName(PRODUCT_NAME);
        productWithOutId.setQuantity(QUANTITY_OLD);
        productWithOutId.setPrice(PRODUCT_PRICE);
        productWithOutId.setQuantity(QUANTITY_OLD);
        productWithOutId.setDiscount(oldDiscount);
        productWithOutId.setCategory(category1);

        productWithId = new Product();
        productWithId.setId(PRODUCT_ID);
        productWithId.setName(PRODUCT_NAME);
        productWithId.setQuantity(QUANTITY_OLD);
        productWithId.setPrice(PRODUCT_PRICE);
        productWithId.setQuantity(QUANTITY_OLD);
        productWithId.setDiscount(oldDiscount);
        productWithId.setCategory(category1);

        productWithNewDiscount = new Product();
        productWithNewDiscount.setId(PRODUCT_ID);
        productWithNewDiscount.setName(PRODUCT_NAME);
        productWithNewDiscount.setQuantity(QUANTITY_OLD);
        productWithNewDiscount.setPrice(PRODUCT_PRICE);
        productWithNewDiscount.setQuantity(QUANTITY_OLD);
        productWithNewDiscount.setDiscount(newDiscount);
        productWithNewDiscount.setCategory(category1);

        productWithNewQuantity = new Product();
        productWithNewQuantity.setId(PRODUCT_ID);
        productWithNewQuantity.setName(PRODUCT_NAME);
        productWithNewQuantity.setQuantity(QUANTITY_OLD);
        productWithNewQuantity.setPrice(PRODUCT_PRICE);
        productWithNewQuantity.setQuantity(QUANTITY_NEW);
        productWithNewQuantity.setDiscount(oldDiscount);
        productWithNewQuantity.setCategory(category1);

        product1 = new Product();
        product1.setId(PRODUCT_ONE_ID);
        product1.setName(PRODUCT_ONE);
        product1.setQuantity(QUANTITY_OLD);
        product1.setPrice(PRODUCT_PRICE);
        product1.setQuantity(QUANTITY_NEW);
        product1.setDiscount(oldDiscount);
        product1.setCategory(category1);

        product2 = new Product();
        product2.setId(PRODUCT_TWO_ID);
        product2.setName(PRODUCT_TWO);
        product2.setQuantity(QUANTITY_OLD);
        product2.setPrice(PRODUCT_PRICE);
        product2.setQuantity(QUANTITY_NEW);
        product2.setDiscount(oldDiscount);
        product2.setCategory(category1);

        product3 = new Product();
        product3.setId(PRODUCT_THREE_ID);
        product3.setName(PRODUCT_THREE);
        product3.setQuantity(QUANTITY_OLD);
        product3.setPrice(PRODUCT_PRICE);
        product3.setQuantity(QUANTITY_NEW);
        product3.setDiscount(oldDiscount);
        product3.setCategory(category2);

        allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        allProductList.add(product3);

        allProductListByCategory = new ArrayList<>();
        allProductListByCategory.add(product1);
        allProductListByCategory.add(product2);

        when(productRepository.save(productWithOutId)).thenReturn(productWithId);
        when(productRepository.getByName(PRODUCT_NAME)).thenReturn(Optional.of(productWithId));
        when(productRepository.getByName(PRODUCT_FAKE_NAME)).thenThrow(ProductServiceException.class);
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productWithId));
        when(productRepository.findById(PRODUCT_FAKE_ID)).thenThrow(ProductServiceException.class);
        when(productRepository.save(productWithNewQuantity)).thenReturn(productWithNewQuantity);
        when(productRepository.save(productWithNewDiscount)).thenReturn(productWithNewDiscount);
        when(productRepository.getAllByCategory(category1)).thenReturn(allProductListByCategory);
        when(productRepository.findAll()).thenReturn(allProductList);
        when(categoryService.getByName(CATEGORY_ONE_NAME)).thenReturn(category1);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() {
        assertEquals(productWithId, productService.create(productWithOutId));
    }

    @Test
    void getByName() {
        assertEquals(productWithId, productService.getByName(PRODUCT_NAME));
        assertThrows(ProductServiceException.class, () -> productService.getByName(PRODUCT_FAKE_NAME));
    }

    @Test
    void getById() {
        assertEquals(productWithId, productService.getById(PRODUCT_ID));
        assertThrows(ProductServiceException.class, () -> productService.getById(PRODUCT_FAKE_ID));
    }

    @Test
    void changeQuantityOfProduct() {
        assertEquals(productWithNewQuantity, productService.sellProduct(PRODUCT_ID, QUANTITY_FOR_PAY));
        assertThrows(ProductServiceException.class, () -> productService.sellProduct(PRODUCT_ID, EXTRA_QUANTITY_FOR_PAY));
        assertThrows(ProductServiceException.class, () -> productService.sellProduct(PRODUCT_FAKE_ID, QUANTITY_FOR_PAY));
    }

    @Test
    void changeDiscountOfProduct() {
        assertEquals(productWithNewDiscount, productService.changeDiscountOfProduct(PRODUCT_ID, newDiscount));
        assertThrows(ProductServiceException.class, () -> productService.changeDiscountOfProduct(PRODUCT_FAKE_ID, newDiscount));
    }

    @Test
    void getAllProducts() {
        assertEquals(allProductList, productService.getAllProducts(null));
        assertEquals(allProductListByCategory, productService.getAllProducts(CATEGORY_ONE_NAME));
    }
}
