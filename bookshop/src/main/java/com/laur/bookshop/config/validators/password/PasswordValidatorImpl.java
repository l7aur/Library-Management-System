package com.laur.bookshop.config.validators.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.laur.bookshop.config.enums.AppMessages.*;

public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, String> {

    private static final String CAPITALS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SMALL = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL = ".,;'[]-=`/+_)(*&^%$#@!~<>?:{}|";
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 255;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isBlank()) {
            addConstraintViolation(context, PASSWORD_VALIDATOR_ERROR_MESSAGE_MISSING);
            return false;
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            addConstraintViolation(context, "Password must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters");
            return false;
        }

        if (notContains(password, CAPITALS)) {
            addConstraintViolation(context, PASSWORD_VALIDATOR_ERROR_MESSAGE_UPPERCASE);
            return false;
        }

        if (notContains(password, SMALL)) {
            addConstraintViolation(context, PASSWORD_VALIDATOR_ERROR_MESSAGE_LOWERCASE);
            return false;
        }

        if (notContains(password, DIGITS)) {
            addConstraintViolation(context, PASSWORD_VALIDATOR_ERROR_MESSAGE_DIGIT);
            return false;
        }

        if (notContains(password, SPECIAL)) {
            addConstraintViolation(context, PASSWORD_VALIDATOR_ERROR_MESSAGE_SPECIAL);
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
