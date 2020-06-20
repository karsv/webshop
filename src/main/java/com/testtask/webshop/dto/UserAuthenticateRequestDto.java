package com.testtask.webshop.dto;

import lombok.Data;

@Data
public class UserAuthenticateRequestDto {
    public String name;
    public String password;
}
