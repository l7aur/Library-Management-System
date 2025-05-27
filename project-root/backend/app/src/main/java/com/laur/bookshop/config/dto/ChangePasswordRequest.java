package com.laur.bookshop.config.dto;

import com.laur.bookshop.config.validators.email.EmailValidator;
import com.laur.bookshop.config.validators.password.PasswordValidator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotEmpty(message = "Email cannot be empty!")
    @EmailValidator
    private String email;

    @NotEmpty(message = "Security code cannot be empty!")
    @Size(min = 6, max = 6, message = "Security code must have 6 digits!")
    private String securityCode;

    @PasswordValidator
    private String password;

    @PasswordValidator
    private String confirmation;
}
