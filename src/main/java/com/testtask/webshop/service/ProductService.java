package com.testtask.webshop.service;

import java.util.List;
import com.testtask.webshop.model.Discount;
import com.testtask.webshop.model.Product;

public interface ProductService {
    Product create(Product product);

    Product getByName(String name);

    Product getById(Long id);

    Product sellProduct(Long id, Long quantity);

    Product changeDiscountOfProduct(Long id, Discount discount);

    List<Product> getAllProducts(String category);
}
