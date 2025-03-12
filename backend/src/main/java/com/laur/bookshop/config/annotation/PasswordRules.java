package com.laur.bookshop.config.annotation;

import com.laur.bookshop.config.exceptions.PasswordException;

public final class PasswordRules {
    private static final String CAPITALS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SMALL = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL = ".,;'[]-=`/+_)(*&^%$#@!~<>?:{}|";
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 20;

    public static void validate(String password) throws PasswordException {
        hasMinimumLength(password);
        hasMaximumLength(password);
        hasSpecialChar(password);
        hasCapital(password);
        hasSmall(password);
        hasDigit(password);
    }

    private static void hasMinimumLength(String password) {
        if(password.length() < MIN_LENGTH)
            throw new PasswordException("Size of the password must be larger than " + MIN_LENGTH + " characters!");
    }

    private static void hasMaximumLength(String password) {
        if(password.length() > MAX_LENGTH)
            throw new PasswordException("Size of the password must be smaller than " + MAX_LENGTH + " characters!");
    }

    private static void hasSpecialChar(String password) {
        if(notContains(password, SPECIAL))
            throw new PasswordException("The password must contain at least a special character!");
    }

    private static void hasCapital(String password) {
        if(notContains(password, CAPITALS))
            throw new PasswordException("The password must contain at least a capital letter!");
    }

    private static void hasSmall(String password) {
        if(notContains(password, SMALL))
            throw new PasswordException("The password must contain at least a small letter!");
    }

    private static void hasDigit(String password) {
        if(notContains(password, DIGITS))
            throw new PasswordException("The password must contain at least a digit!");
    }

    private static boolean notContains(String password, String requiredChars) {
        for(char c : password.toCharArray()) {
            if(requiredChars.indexOf(c) != -1)
                return false;
        }
        return true;
    }
}