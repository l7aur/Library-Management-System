package com.laur.bookshop.controllers;

import com.laur.bookshop.model.SendEmailRequest;
import com.laur.bookshop.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/sendMail")
    public Boolean sendMail(@RequestBody SendEmailRequest er)
    {
        return emailService.sendSimpleMail(er);
    }
}