package com.testtask.webshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String price;
    private String discount;
    private Long quantity;
    private String category;
}
