package com.laur.bookshop.config.exceptions;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {
        super("Email not found");
    }
}
