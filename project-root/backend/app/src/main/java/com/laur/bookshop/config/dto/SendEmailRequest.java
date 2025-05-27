package com.laur.bookshop.config.dto;

import com.laur.bookshop.config.validators.email.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendEmailRequest {

    @EmailValidator
    private String to;
}
