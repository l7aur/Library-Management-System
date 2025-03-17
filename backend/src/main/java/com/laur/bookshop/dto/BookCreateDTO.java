package com.laur.bookshop.dto;

import com.laur.bookshop.config.annotation.isbn.ValidISBN;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BookCreateDTO {
    @ValidISBN
    private String isbn;

    @NotBlank(message = "The title is mandatory!")
    @Size(min = 2, max = 255, message = "The title must have between 2 and 255 characters!")
    private String title;

    @NotNull(message = "The publish year is mandatory!")
    @Min(value = 1000, message = "No book can be published for that long!")
    @Max(value = 2025, message = "No book can be published in the future!")
    private Integer publishYear;

    @NotNull(message = "The publisher is mandatory!")
    private UUID publisherId;

    @NotEmpty(message = "The authors are mandatory!")
    private List<UUID> authorIds;

    @Positive(message = "The price must be positive")
    @NotNull(message = "The price is mandatory!")
    private Double price;

    @Positive(message = "The stock must be positive")
    @NotNull(message = "The stock is mandatory!")
    private Integer stock;
}
