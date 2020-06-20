package com.testtask.webshop.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestAddMoneyDto {
    private Long id;
    private BigDecimal money;
}
