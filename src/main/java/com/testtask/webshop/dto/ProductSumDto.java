package com.testtask.webshop.dto;

import java.util.Comparator;
import com.testtask.webshop.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSumDto implements Comparable<ProductSumDto> {
    private Product product;
    private Long numberOfItems;

    @Override
    public int compareTo(ProductSumDto productSumDto) {
        return Comparator.comparing(ProductSumDto::getProduct)
                .compare(this, productSumDto);
    }
}
