package com.laur.bookshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AuthorCreateDTO {
    @NotBlank(message = "First name is mandatory!")
    @Size(min = 2, max = 50, message = "First name should be between 2 and 50 characters!")
    private String firstName;

    @NotBlank(message = "Las name is mandatory!")
    @Size(min = 2, max = 50, message = "Last name should be between 2 and 50 characters!")
    private String lastName;

    @Size(max = 50, message = "Alias cannot exceed 50 characters!")
    private String alias;

    @NotBlank(message = "Nationality is mandatory!")
    @Size(min = 2, max = 50, message = "Nationality should be between 2 and 50 characters!")
    private String nationality;

    List<String> books;
}
