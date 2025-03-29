package com.laur.bookshop.config.validators.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, String> {

    private static final String CAPITALS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SMALL = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL = ".,;'[]-=`/+_)(*&^%$#@!~<>?:{}|";
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 20;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isBlank()) {
            addConstraintViolation(context, "Password cannot be empty");
            return false;
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            addConstraintViolation(context, "Password must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters");
            return false;
        }

        if (notContains(password, CAPITALS)) {
            addConstraintViolation(context, "Password must contain at least one uppercase letter");
            return false;
        }

        if (notContains(password, SMALL)) {
            addConstraintViolation(context, "Password must contain at least one lowercase letter");
            return false;
        }

        if (notContains(password, DIGITS)) {
            addConstraintViolation(context, "Password must contain at least one digit");
            return false;
        }

        if (notContains(password, SPECIAL)) {
            addConstraintViolation(context, "Password must contain at least one special character");
            return false;
        }

        return true;
    }

    private boolean notContains(String password, String requiredChars) {
        return password.chars().noneMatch(c -> requiredChars.indexOf(c) != -1);
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
