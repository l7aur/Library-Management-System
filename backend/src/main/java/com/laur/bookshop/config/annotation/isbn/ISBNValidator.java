package com.laur.bookshop.config.annotation.isbn;

import com.laur.bookshop.config.exceptions.ISBNException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ValidISBN, String> {
    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        try {
            ISBNRules.validate(isbn);
            return true;
        }
        catch (ISBNException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addConstraintViolation();
            return false;
        }
    }
}
