package com.testtask.webshop.service;

import com.testtask.webshop.model.User;

public interface AuthenticationService {
    User authentication(String name, String password);
}
