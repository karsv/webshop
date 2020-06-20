package com.testtask.webshop.controllers;


import com.testtask.webshop.dto.UserAuthenticateRequestDto;
import com.testtask.webshop.dto.UserResponseLoginDto;
import com.testtask.webshop.exceptions.AuthenticationException;
import com.testtask.webshop.model.User;
import com.testtask.webshop.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {
    private final AuthenticationService authenticateService;

    public AuthenticateController(AuthenticationService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("/login")
    public UserResponseLoginDto login(@RequestBody UserAuthenticateRequestDto person) {
        return convertUserToUserResponseDto(authenticateService.
                authentication(person.getName(), person.getPassword()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AuthenticationException validationError(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().get(1).getDefaultMessage();
        return new AuthenticationException(message);
    }

    private UserResponseLoginDto convertUserToUserResponseDto(User user) {
        UserResponseLoginDto userResponseLoginDto = new UserResponseLoginDto();
        userResponseLoginDto.setId(user.getId());
        userResponseLoginDto.setName(user.getName());
        userResponseLoginDto.setPassword(user.getPassword());
        userResponseLoginDto.setRole(user.getRole().toString());
        return userResponseLoginDto;
    }
}
