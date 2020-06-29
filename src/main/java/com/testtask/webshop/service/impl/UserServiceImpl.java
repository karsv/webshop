package com.testtask.webshop.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import com.testtask.webshop.exceptions.UserServiceException;
import com.testtask.webshop.model.Roles;
import com.testtask.webshop.model.User;
import com.testtask.webshop.repository.UserRepository;
import com.testtask.webshop.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRole(Roles.USER);
        user.setCash(BigDecimal.valueOf(0));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User createCustomUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserServiceException("No such user!");
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserServiceException("No such user!");
    }

    @Override
    @Transactional
    public User addMoney(Long userId, BigDecimal money) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BigDecimal newCash = user.getCash().add(money);
            user.setCash(newCash);
            return userRepository.save(user);
        }

        throw new UserServiceException("No such user!");
    }

    @Override
    @Transactional
    public User subtractMoney(Long userId, BigDecimal money) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserServiceException("No such user!");
        }
        User user = optionalUser.get();
        if (!checkMoneyOnAccount(user, money)) {
            throw new UserServiceException("Not enough money on account!");
        }
        BigDecimal newCash = user.getCash().subtract(money);
        user.setCash(newCash);
        return userRepository.save(user);
    }

    private boolean checkMoneyOnAccount(User user, BigDecimal money) {
        return user.getCash().compareTo(money) != -1;
    }
}
