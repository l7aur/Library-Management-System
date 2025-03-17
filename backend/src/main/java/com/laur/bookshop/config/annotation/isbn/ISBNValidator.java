package com.laur.bookshop.config.annotation.isbn;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ValidISBN, String> {
    private static final String ISBN_REGEX = "^(97[89])?\\d{9}(\\d|X)$";

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.isBlank()) {
            return false;
        }
        return isbn.matches(ISBN_REGEX);
    }
}
