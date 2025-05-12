package com.laur.bookshop.model;

public record ChangePasswordRequest(
        String email,
        String securityCode,
        String password,
        String confirmation
) {
}
