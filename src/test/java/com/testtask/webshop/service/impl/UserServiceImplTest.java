package com.testtask.webshop.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import com.testtask.webshop.exceptions.UserServiceException;
import com.testtask.webshop.model.Roles;
import com.testtask.webshop.model.User;
import com.testtask.webshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    private final static String USER_NAME = "user";
    private final static String FAKE_NAME = "fake_user";
    private final static String PASSWORD = "123";
    private final static String CUSTOM_USER_NAME = "custom_user";
    private final static Long USER_ID = 1L;
    private final static Long CUSTOM_USER_ID = 1L;
    private final static Long FAKE_ID = 12L;
    private final static Roles USER_ROLE = Roles.USER;
    private final static Roles CUSTOM_USER_ROLE = Roles.ADMIN;
    private final static BigDecimal USER_CASH = BigDecimal.valueOf(10);
    private final static BigDecimal ADDED_CASH = BigDecimal.valueOf(2);
    private final static BigDecimal SUBSTRACTED_CASH = BigDecimal.valueOf(5);
    private final static BigDecimal EXTRA_SUBSTRACTED_CASH = BigDecimal.valueOf(100);
    private final static BigDecimal USER_CASH_AFTER_ADDED = BigDecimal.valueOf(12);
    private final static BigDecimal USER_CASH_AFTER_SUBSTRACTED = BigDecimal.valueOf(5);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User newUserWithId;
    private User newUserWithoutId;
    private User savedUser;
    private User customUser;
    private User savedCustomUser;
    private User userAfterAddMoney;
    private User userAfterSubtractedMoney;

    @BeforeEach
    private void setUp() {
        newUserWithoutId = new User();
        newUserWithoutId.setName(USER_NAME);
        newUserWithoutId.setPassword(PASSWORD);
        newUserWithoutId.setRole(USER_ROLE);
        newUserWithoutId.setCash(BigDecimal.valueOf(0));

        newUserWithId = new User();
        newUserWithId.setId(USER_ID);
        newUserWithId.setName(USER_NAME);
        newUserWithId.setPassword(PASSWORD);
        newUserWithId.setRole(USER_ROLE);
        newUserWithId.setCash(BigDecimal.valueOf(0));

        savedUser = new User();
        savedUser.setId(USER_ID);
        savedUser.setName(USER_NAME);
        savedUser.setPassword(PASSWORD);
        savedUser.setRole(USER_ROLE);
        savedUser.setCash(USER_CASH);

        customUser = new User();
        customUser.setName(CUSTOM_USER_NAME);
        customUser.setPassword(PASSWORD);
        customUser.setRole(CUSTOM_USER_ROLE);

        savedCustomUser = new User();
        savedCustomUser.setId(CUSTOM_USER_ID);
        savedCustomUser.setName(CUSTOM_USER_NAME);
        savedCustomUser.setPassword(PASSWORD);
        savedCustomUser.setRole(CUSTOM_USER_ROLE);

        userAfterAddMoney = new User();
        userAfterAddMoney.setId(USER_ID);
        userAfterAddMoney.setName(USER_NAME);
        userAfterAddMoney.setPassword(PASSWORD);
        userAfterAddMoney.setRole(USER_ROLE);
        userAfterAddMoney.setCash(USER_CASH_AFTER_ADDED);

        userAfterSubtractedMoney = new User();
        userAfterSubtractedMoney.setId(USER_ID);
        userAfterSubtractedMoney.setName(USER_NAME);
        userAfterSubtractedMoney.setPassword(PASSWORD);
        userAfterSubtractedMoney.setRole(USER_ROLE);
        userAfterSubtractedMoney.setCash(USER_CASH_AFTER_SUBSTRACTED);

        when(userRepository.save(newUserWithoutId)).thenReturn(newUserWithId);
        when(userRepository.save(customUser)).thenReturn(savedCustomUser);
        when(userRepository.save(userAfterAddMoney)).thenReturn(userAfterAddMoney);
        when(userRepository.save(userAfterSubtractedMoney)).thenReturn(userAfterSubtractedMoney);
        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.of(savedUser));
        when(userRepository.findByName(FAKE_NAME)).thenThrow(UserServiceException.class);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(savedUser));
        when(userRepository.findById(FAKE_ID)).thenThrow(UserServiceException.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUser() {
        assertEquals(newUserWithId, userService.createUser(USER_NAME, PASSWORD));
    }

    @Test
    void createCustomUser() {
        assertEquals(savedCustomUser, userService.createCustomUser(customUser));
    }

    @Test
    void getByName() {
        assertEquals(savedUser, userService.getByName(USER_NAME));
        assertThrows(UserServiceException.class, () -> userService.getByName(FAKE_NAME));
    }

    @Test
    void getById() {
        assertEquals(savedUser, userService.getById(USER_ID));
        assertThrows(UserServiceException.class, () -> userService.getById(FAKE_ID));
    }

    @Test
    void addMoney() {
        assertEquals(userAfterAddMoney, userService.addMoney(USER_ID, ADDED_CASH));
        assertThrows(UserServiceException.class, () -> userService.addMoney(FAKE_ID, ADDED_CASH));
    }

    @Test
    void subtractMoney() {
        assertEquals(userAfterSubtractedMoney, userService.subtractMoney(USER_ID, SUBSTRACTED_CASH));
        assertThrows(UserServiceException.class, () -> userService.subtractMoney(FAKE_ID, SUBSTRACTED_CASH));
        assertThrows(UserServiceException.class, () -> userService.subtractMoney(FAKE_ID, EXTRA_SUBSTRACTED_CASH));
    }
}
