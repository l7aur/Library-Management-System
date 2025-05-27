package com.laur.bookshop.config.validators.isbn;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsbnValidatorImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsbnValidator {
    String message() default "Invalid ISBN!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
