package com.laur.bookshop.services;

import com.laur.bookshop.config.dto.ConfirmationEmailData;
import com.laur.bookshop.model.EmailDetails;
import com.laur.bookshop.config.dto.SendEmailRequest;
import com.laur.bookshop.repositories.EmailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private EmailRepo repo;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private DefaultErrorAttributes errorAttributes;

    public Boolean sendSimpleMail(SendEmailRequest er) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String securityCode = getSecurityCode();
            mailMessage.setFrom(from);
            mailMessage.setTo(er.getTo());
            mailMessage.setSubject("Password Reset Security Code");
            mailMessage.setText("Your security code is: " + securityCode);
            saveMailDetails(er.getTo(), securityCode);
            mailSender.send(mailMessage);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Boolean sendConfirmationEmail(String to, Integer orderNumber, List<ConfirmationEmailData> items) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(from);
            mailMessage.setTo(to);
            mailMessage.setSubject("Confirmation for order #" + orderNumber);

            double totalPrice = 0.0;
            boolean isMissingItem = false;
            for (ConfirmationEmailData item : items) {
                if(item.title() == null && item.quantity() == -1 && item.price() == 0.0) {
                    isMissingItem = true;
                    continue;
                }
                totalPrice += item.price() * item.quantity();
            }
            if(totalPrice != 0.0) {
                totalPrice += 5.0; // transport fee
            }
            StringBuilder text = new StringBuilder();
            text.append("This is the confirmation of order #")
                    .append(orderNumber)
                    .append("\n Thank you for your purchase!\n")
                    .append("Total: $")
                    .append(totalPrice)
                    .append("\n\n");
            if(isMissingItem)
                text.append("NOTE: SOME ITEMS YOU PURCHASED ARE NOT AVAILABLE AT THIS TIME! CHECK THE ENVOY!\n\n");

            for(ConfirmationEmailData item : items) {
                if(item.title() == null && item.quantity() == -1 && item.price() == 0.0)
                    continue;
                text.append("\n\t")
                        .append(item.title())
                        .append(" - ")
                        .append(item.quantity())
                        .append(" x $")
                        .append(item.price());
            }
            text.append("\n\nThank you! We'll reach to you soon!\n");
            mailMessage.setText(text.toString());
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
        return repo.findByReceiver(email);
    }

    private void saveMailDetails(String to, String securityCode) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setCode(securityCode);
        emailDetails.setReceiver(to);
        emailDetails.setExpirationTime(LocalTime.now().plusMinutes(5));
        repo.save(emailDetails);
    }

    private String getSecurityCode() {
        Random random = new Random();
        StringBuilder securityCode = new StringBuilder();
        for(int i = 0; i < 6; i++)
            securityCode.append(random.nextInt(10));
        return securityCode.toString();
    }
}