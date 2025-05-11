package com.laur.bookshop.model;

public record LoginResponse(
        AppUser appUser,
        String errorMessage,
        String token) {
}
