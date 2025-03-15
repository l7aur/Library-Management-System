package com.laur.bookshop.model;

import jakarta.validation.constraints.*;
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

    @NotBlank(message = "The publish year is mandatory!")
    @Min(value = 1000, message = "No book can be published for that long!")
    @Max(value = 2025, message = "No book can be published in the future!")
    private Integer publishYear;

    @NotBlank(message = "The publisher is mandatory!")
    @Size(min = 2, max = 50, message = "The publisher must have between 2 and 50 characters!")
    private String publisher;

    @NotBlank(message = "The authors are mandatory!")
    private List<String> authors;

    @NotBlank(message = "The price is mandatory!")
    @Positive
    @NotNull
    private Double price;

    @NotBlank(message = "The stock is mandatory!")
    @Positive
    @NotNull
    private Integer stock;
}
