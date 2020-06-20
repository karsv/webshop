package com.testtask.webshop.service.impl;

import com.testtask.webshop.exceptions.AuthenticationException;
import com.testtask.webshop.model.User;
import com.testtask.webshop.service.AuthenticationService;
import com.testtask.webshop.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder,
                                     UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public User authentication(String name, String password) {
        User user = userService.getByName(name);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Wrong email or password!");
        }
        return user;
    }
}
