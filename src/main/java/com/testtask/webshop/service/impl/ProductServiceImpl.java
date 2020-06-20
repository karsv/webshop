package com.testtask.webshop.service.impl;

import java.util.List;
import java.util.Optional;
import com.testtask.webshop.exceptions.ProductServiceException;
import com.testtask.webshop.model.Category;
import com.testtask.webshop.model.Discount;
import com.testtask.webshop.model.Product;
import com.testtask.webshop.repository.ProductRepository;
import com.testtask.webshop.service.CategoryService;
import com.testtask.webshop.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getByName(String name) {
        Optional<Product> product = productRepository.getByName(name);
        if (product.isPresent()) {
            return product.get();
        }
        throw new ProductServiceException("No such product: " + name + "!");
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new ProductServiceException("No such product: " + id + "!");
    }

    @Override
    public Product sellProduct(Long id, Long quantity) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (quantity > product.getQuantity()) {
                throw new ProductServiceException("Not enough items for sell!");
            }
            Long oldQuantity = product.getQuantity();
            product.setQuantity(oldQuantity - quantity);
            return productRepository.save(product);
        }
        throw new ProductServiceException("No such product: " + id + "!");
    }

    @Override
    public Product changeDiscountOfProduct(Long id, Discount discount) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setDiscount(discount);
            return productRepository.save(product);
        }
        throw new ProductServiceException("No such product: " + id + "!");
    }

    @Override
    public List<Product> getAllProducts(String categoryName) {
        if (categoryName == null) {
            return productRepository.findAll();
        }
        Category category = categoryService.getByName(categoryName);
        return productRepository.getAllByCategory(category);
    }
}
