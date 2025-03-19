package com.laur.bookshop.dto;

import com.laur.bookshop.config.annotation.password.ValidPassword;
import com.laur.bookshop.model.AppUserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppUserCreateDTO {
    @NotEmpty(message = "Username cannot be empty!")
    @Size(min = 2, max = 20, message = "Username must have between 2 and 20 characters!")
    private String username;

    @ValidPassword
    private String password;

    @NotEmpty(message = "Role is mandatory!")
    private String role;

    @NotBlank(message = "User must have a first name!")
    @Size(min = 2, max = 50, message = "First name must have between 2 and 50 characters!")
    private String firstName;

    @NotEmpty(message = "User must have a last name!")
    @Size(min = 2, max = 50, message = "Last name must have between 2 and 50 characters!")
    private String lastName;

    public AppUserRole getRole() throws IllegalArgumentException {
        return AppUserRole.valueOf(role.toUpperCase());
    }
}
