package com.testtask.webshop.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import com.testtask.webshop.dto.ProductRequestDto;
import com.testtask.webshop.dto.ProductResponseDto;
import com.testtask.webshop.exceptions.ProductServiceException;
import com.testtask.webshop.model.Product;
import com.testtask.webshop.service.CategoryService;
import com.testtask.webshop.service.DiscounService;
import com.testtask.webshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    private final ProductService productService;
    private final CategoryService categoryService;
    private final DiscounService discounService;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             DiscounService discounService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.discounService = discounService;
    }

    @PostMapping
    @Transactional
    public Product create(@RequestBody ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setPrice(BigDecimal.valueOf(productRequestDto.getPrice()));
        product.setQuantity(productRequestDto.getQuantity());
        product.setCategory(categoryService.getByName(productRequestDto.getCategory()));
        product.setDiscount(discounService.getByValue(productRequestDto.getDiscount()));
        return productService.create(product);
    }

    @GetMapping
    public List<ProductResponseDto> getAll(@RequestParam(required = false) String category) {
        return productService.getAllProducts(category).stream()
                .map(p -> convertProductToProductResponseDto(p))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProductServiceException validationError(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().get(1).getDefaultMessage();
        return new ProductServiceException(message);
    }

    private ProductResponseDto convertProductToProductResponseDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setQuantity(product.getQuantity());
        productResponseDto.setDiscount(product.getDiscount().getValue().toString() + "%");
        productResponseDto.setPrice(product.getPrice()
                .setScale(2, RoundingMode.HALF_EVEN).toString());
        productResponseDto.setCategory(product.getCategory().getName());
        return productResponseDto;
    }
}
