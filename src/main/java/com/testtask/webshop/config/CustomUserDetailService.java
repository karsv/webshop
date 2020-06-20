package com.testtask.webshop.config;

import com.testtask.webshop.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        com.testtask.webshop.model.User user = userService.getByName(name);
        User.UserBuilder userBuilder = User.withUsername(name);
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRole().toString());
        return userBuilder.build();
    }
}
