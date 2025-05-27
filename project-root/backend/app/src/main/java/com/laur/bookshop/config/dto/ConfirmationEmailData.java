package com.laur.bookshop.config.dto;

public record ConfirmationEmailData(
        String title,
        Integer quantity,
        Double price) {
}
