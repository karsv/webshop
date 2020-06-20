package com.testtask.webshop.controllers;

import java.util.List;
import java.util.stream.Collectors;
import com.testtask.webshop.dto.DiscountRequestDto;
import com.testtask.webshop.dto.DiscountsResponseDto;
import com.testtask.webshop.model.Discount;
import com.testtask.webshop.service.DiscounService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
public class DiscountController {
    private final DiscounService discounService;

    public DiscountController(DiscounService discounService) {
        this.discounService = discounService;
    }

    @GetMapping
    public List<DiscountsResponseDto> getAll() {
        return discounService.getAll().stream()
                .map(d -> convertDiscoutToDiscountResponseDto(d))
                .collect(Collectors.toList());
    }

    @PostMapping
    public DiscountsResponseDto create(@RequestBody DiscountRequestDto discountRequestDto) {
        Discount discount = new Discount();
        discount.setValue(discountRequestDto.getValue());
        return convertDiscoutToDiscountResponseDto(discounService.create(discount));
    }

    private DiscountsResponseDto convertDiscoutToDiscountResponseDto(Discount d) {
        DiscountsResponseDto discountsResponseDto = new DiscountsResponseDto();
        discountsResponseDto.setId(d.getId());
        discountsResponseDto.setValue(d.getValue() + "%");
        return discountsResponseDto;
    }
}
