package com.testtask.webshop.validators.passwordEqualValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.testtask.webshop.dto.UserRegistrationDto;

public class CustomPasswordEqualValidator implements
        ConstraintValidator<PasswordEqualValidator, UserRegistrationDto> {
    @Override
    public boolean isValid(UserRegistrationDto user,
                           ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword()
                .equals(user.getRepeatPassword());
    }
}
