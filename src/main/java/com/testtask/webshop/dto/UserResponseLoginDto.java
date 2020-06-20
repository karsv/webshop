package com.testtask.webshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseLoginDto {
    private Long id;
    private String name;
    private String password;
    private String role;
}
