package com.laur.bookshop.config.exceptions;

public class ExpiredSecurityCodeException extends RuntimeException {
    public ExpiredSecurityCodeException() {
        super("The passcode expired!");
    }
}
