package com.laur.bookshop.config.dto;

import com.laur.bookshop.config.validators.email.EmailValidator;
import lombok.Data;

@Data
public class SendEmailRequest {

    @EmailValidator
    private String to;
}
