package com.testtask.webshop.controllers;

import java.math.BigDecimal;
import com.testtask.webshop.config.CustomUserDetailService;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.model.Discount;
import com.testtask.webshop.model.Product;
import com.testtask.webshop.service.CategoryService;
import com.testtask.webshop.service.DiscounService;
import com.testtask.webshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
@WithMockUser(username = "ADMIN", password = "123", authorities = {"ADMIN"})
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private DiscounService discounService;

    @MockBean
    @Qualifier("customUserDetailService")
    private CustomUserDetailService customUserDetailService;

    private Product productproduct;
    private Category category;
    private Discount discount;

    @BeforeEach
    private void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Products");

        discount = new Discount();
        discount.setId(1L);
        discount.setValue(10L);

        productproduct = new Product();
        productproduct.setId(1L);
        productproduct.setName("Product");
        productproduct.setQuantity(1L);
        productproduct.setPrice(BigDecimal.valueOf(1));
        productproduct.setDiscount(discount);
        productproduct.setCategory(category);

        when(categoryService.getByName("Products")).thenReturn(category);
        when(discounService.getByValue(10L)).thenReturn(discount);

    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/product/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Buratino\",\n" +
                        "    \"price\": \"12\",\n" +
                        "    \"discount\": \"10\",\n" +
                        "    \"quantity\": \"3\",\n" +
                        "    \"category\": \"Books\"\n" +
                        "}").contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getAll() {
    }
}
