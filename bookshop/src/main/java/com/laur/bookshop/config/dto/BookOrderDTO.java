package com.laur.bookshop.config.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class BookOrderDTO {
    private UUID id;

    @NotEmpty(message = "Username must not be empty!")
    private String username;

    @NotNull(message = "Order number must not be empty!")
    @Positive(message = "Order number must be positive!")
    private Integer orderNumber;

    @NotNull(message = "Date must not be empty!")
    private Date orderDate;

    @NotEmpty(message = "Book id must not be empty!")
    private String bookId;

    @NotNull(message = "Price must not be null!")
    @Positive(message = "Price must be positive!")
    private Double price;
}
