package com.laur.bookshop.config.dto;

import com.laur.bookshop.config.validators.email.EmailValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ConfirmationEmailDTO {

    @EmailValidator
    private String to;

    @Positive(message = "Order number must be positive!")
    @NotNull(message = "Order number must exist!")
    private Integer orderNumber;
}
