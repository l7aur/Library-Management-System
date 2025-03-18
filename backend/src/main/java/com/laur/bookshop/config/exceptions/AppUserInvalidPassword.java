package com.laur.bookshop.config.exceptions;

public class AppUserInvalidPassword extends RuntimeException{
    public AppUserInvalidPassword(String message) {
        super(message);
    }
}
