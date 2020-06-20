package com.testtask.webshop.model;

import java.math.BigDecimal;
import java.util.Comparator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product implements Comparable<Product> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    @ManyToOne
    private Discount discount;
    private Long quantity;
    @ManyToOne
    private Category category;


    @Override
    public int compareTo(Product product) {
        return Comparator.comparing(Product::getPrice)
                .thenComparing(Product::getDiscount).compare(this, product);
    }
}
