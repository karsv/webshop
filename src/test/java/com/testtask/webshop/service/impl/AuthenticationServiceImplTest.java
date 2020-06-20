package com.testtask.webshop.service.impl;

import com.testtask.webshop.exceptions.AuthenticationException;
import com.testtask.webshop.exceptions.UserServiceException;
import com.testtask.webshop.model.User;
import com.testtask.webshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationServiceImplTest {
    private static final String REAL_NAME = "user";
    private static final String FAKE_NAME = "stranger";
    private static final String REAL_PASSWORD = "123";
    private static final String DECODED_PASSWORD = "decoded_password";
    private static final String FAKE_PASSWORD = "ogon";

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User user;

    @BeforeEach
    private void setUp() {
        user = new User();
        user.setId(1L);
        user.setName(REAL_NAME);
        user.setPassword(DECODED_PASSWORD);

        when(passwordEncoder.matches(REAL_PASSWORD, DECODED_PASSWORD)).thenReturn(true);
        when(passwordEncoder.matches(FAKE_PASSWORD, DECODED_PASSWORD)).thenReturn(false);
        when(userService.getByName(REAL_NAME)).thenReturn(user);
        when(userService.getByName(FAKE_NAME)).thenThrow(UserServiceException.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void authentication() {
        assertEquals(user, authenticationService.authentication(REAL_NAME, REAL_PASSWORD));
        assertThrows(UserServiceException.class,
                () -> authenticationService.authentication(FAKE_NAME, REAL_PASSWORD));
        assertThrows(AuthenticationException.class,
                () -> authenticationService.authentication(REAL_NAME, FAKE_PASSWORD));
    }
}
