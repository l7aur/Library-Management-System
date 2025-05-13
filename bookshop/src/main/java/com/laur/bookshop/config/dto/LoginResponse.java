package com.laur.bookshop.config.dto;

import com.laur.bookshop.model.AppUser;

public record LoginResponse(
        AppUser appUser,
        String errorMessage,
        String token) {
}
