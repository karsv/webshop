package com.testtask.webshop.service;

import java.math.BigDecimal;
import com.testtask.webshop.model.User;

public interface UserService {
    User createUser(String name, String password);

    User createCustomUser(User user);

    User getByName(String name);

    User getById(Long id);

    User addMoney(Long userId, BigDecimal money);

    User subtractMoney(Long userId, BigDecimal money);
}
