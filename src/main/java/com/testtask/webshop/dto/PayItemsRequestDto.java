package com.testtask.webshop.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayItemsRequestDto {
    private Long userId;
    private List<ProductBuyDto> products;
}
