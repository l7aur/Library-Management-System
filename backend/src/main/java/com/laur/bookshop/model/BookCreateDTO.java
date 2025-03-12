package com.laur.bookshop.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BookCreateDTO {
    @NotBlank(message = "ISBN is mandatory!")
    @Size(min = 13, max = 13, message = "ISBN must have exactly 13 digits!")
    private String isbn;

    @NotBlank(message = "The title is mandatory!")
    @Size(min = 2, max = 255, message = "The title must have between 2 and 255 characters!")
    private String title;

    @Size(max = 2025, message = "No book can be published in the future!")
    private Integer publishYear;

    @NotEmpty(message = "The publisher is mandatory!")
    @Size(min = 2, max = 50, message = "The publisher must have between 2 and 50 characters!")
    private String publisher;

    @NotEmpty(message = "The authors are mandatory!")
    private List<String> authors;
}
