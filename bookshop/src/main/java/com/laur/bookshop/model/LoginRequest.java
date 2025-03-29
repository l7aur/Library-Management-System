package com.laur.bookshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class LoginRequest {
    private String username;
    private String password;
}
