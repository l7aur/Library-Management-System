package com.laur.bookshop.config.dto;

import com.laur.bookshop.config.validators.email.EmailValidator;
import com.laur.bookshop.config.validators.password.PasswordValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class AppUserDTO {
    private UUID id;

    @NotEmpty(message = "Username cannot be empty!")
    @Size(min = 2, max = 20, message = "Username must have between 2 and 20 characters!")
    private String username;

    @PasswordValidator
    private String password;

    @NotEmpty(message = "Role is mandatory!")
    private String role;

    @NotBlank(message = "User must have a first name!")
    @Size(min = 2, max = 50, message = "First name must have between 2 and 50 characters!")
    private String firstName;

    @NotEmpty(message = "User must have a last name!")
    @Size(min = 2, max = 50, message = "Last name must have between 2 and 50 characters!")
    private String lastName;

    @NotEmpty(message = "An e-mail must be provided!")
    @EmailValidator
    private String email;

    public String getRole() {
        return role.toUpperCase();
    }
}
