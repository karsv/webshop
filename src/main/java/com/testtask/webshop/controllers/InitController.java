package com.testtask.webshop.controllers;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.model.Discount;
import com.testtask.webshop.model.Product;
import com.testtask.webshop.model.Roles;
import com.testtask.webshop.model.User;
import com.testtask.webshop.service.CategoryService;
import com.testtask.webshop.service.DiscounService;
import com.testtask.webshop.service.ProductService;
import com.testtask.webshop.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitController {
    private final ProductService productService;
    private final DiscounService discounService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitController(ProductService productService,
                          DiscounService discounService,
                          CategoryService categoryService,
                          UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.discounService = discounService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void init() {
        User admin = new User();
        admin.setName("ADMIN");
        admin.setRole(Roles.ADMIN);
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setCash(BigDecimal.valueOf(0));
        userService.createCustomUser(admin);

        Discount discount0 = new Discount();
        discount0.setValue(0L);
        Discount discount5 = new Discount();
        discount5.setValue(5L);
        Discount discount10 = new Discount();
        discount10.setValue(10L);
        Discount discount20 = new Discount();
        discount20.setValue(20L);
        discounService.create(discount0);
        discounService.create(discount5);
        discounService.create(discount10);
        discounService.create(discount20);

        Category categoryBooks = new Category();
        categoryBooks.setName("Books");
        Category categoryPencils = new Category();
        categoryPencils.setName("Pencils");
        Category categoryHeadphones = new Category();
        categoryHeadphones.setName("HeadPhones");
        categoryService.create(categoryBooks);
        categoryService.create(categoryPencils);
        categoryService.create(categoryHeadphones);

        Product book1 = new Product();
        book1.setName("Kobzar");
        book1.setPrice(BigDecimal.valueOf(100));
        book1.setQuantity(10L);
        book1.setDiscount(discount10);
        book1.setCategory(categoryBooks);

        Product book2 = new Product();
        book2.setName("Voyna i mir");
        book2.setPrice(BigDecimal.valueOf(400));
        book2.setQuantity(5L);
        book2.setDiscount(discount5);
        book2.setCategory(categoryBooks);

        Product pencil = new Product();
        pencil.setName("Pencil 2B");
        pencil.setPrice(BigDecimal.valueOf(5));
        pencil.setQuantity(100L);
        pencil.setDiscount(discount0);
        pencil.setCategory(categoryPencils);

        Product headphones = new Product();
        headphones.setName("Headphones white");
        headphones.setPrice(BigDecimal.valueOf(1200.0));
        headphones.setQuantity(10L);
        headphones.setDiscount(discount20);
        headphones.setCategory(categoryHeadphones);

        productService.create(book1);
        productService.create(book2);
        productService.create(pencil);
        productService.create(headphones);
    }
}
