package com.example.ecommerce_spring.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LowerCaseValidation implements ConstraintValidator<LowerCase, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) return true; // make @Blank handle it

        return value.equals(value.toLowerCase());
    }
}
