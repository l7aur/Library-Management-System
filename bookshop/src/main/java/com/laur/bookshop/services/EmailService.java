package com.laur.bookshop.services;

import com.laur.bookshop.model.EmailDetails;
import com.laur.bookshop.config.dto.SendEmailRequest;
import com.laur.bookshop.repositories.EmailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private EmailRepo repo;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public Boolean sendSimpleMail(SendEmailRequest er) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            Integer securityCode = getSecurityCode();
            mailMessage.setFrom(from);
            mailMessage.setTo(er.getTo());
            mailMessage.setText("Your security code is: " + securityCode);
            mailMessage.setSubject("Password Reset Security Code");
            saveMailDetails(er.getTo(), securityCode);
            mailSender.send(mailMessage);
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }

    public void delete(String receiverEmail) {
        repo.deleteByReceiver(receiverEmail);
    }

    public List<Optional<EmailDetails>> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    private void saveMailDetails(String to, Integer securityCode) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setCode(securityCode.toString());
        emailDetails.setReceiver(to);
        emailDetails.setExpirationTime(LocalTime.now().plusMinutes(5));
        repo.save(emailDetails);
    }

    private Integer getSecurityCode() {
        Random random = new Random();
        int securityCode = 0;
        for(int i = 0; i < 6; i++)
            securityCode = securityCode * 10 + random.nextInt(10);
        return securityCode;
    }
}