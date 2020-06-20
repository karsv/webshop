package com.testtask.webshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private String name;
    private Double price;
    private Long discount;
    private Long quantity;
    private String category;
}
