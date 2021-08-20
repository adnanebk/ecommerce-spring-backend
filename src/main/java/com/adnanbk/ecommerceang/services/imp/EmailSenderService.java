package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.exceptions.InvalidTokenException;
import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.models.ConfirmationToken;
import com.adnanbk.ecommerceang.reposetories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
public class EmailSenderService {

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.name}")
    private String name;
    @Value("${spring.mail.email}")
    private String email;
    @Value("${api.url}")
    private String url;
    private ConfirmationTokenRepository confirmationTokenRepo;

    public EmailSenderService(JavaMailSender javaMailSender, ConfirmationTokenRepository confirmationTokenRepo) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenRepo = confirmationTokenRepo;
    }

    @Async
    public void sendEmailConfirmation(AppUser user) {
        String destAddress = user.getEmail();

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        String verifyURL = url + "/verify?token=" + confirmationTokenRepo.save(confirmationToken).getConfirmationToken();


        String subject = "Please verify your registration";
        String content = String.format("Dear %s,<br>Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"%s\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br> %s.", user.getFirstName(), verifyURL, name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(email, name);

        helper.setTo(destAddress);
        helper.setSubject(subject);

        helper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
          throw new InvalidTokenException("We could not verify the email");
        }
        javaMailSender.send(message);
    }

    public AppUser verifyToken(String _token) {
        if (_token != null && !_token.isEmpty()) {
            var token = confirmationTokenRepo.findById(_token)
                                       .orElseThrow(()->new InvalidTokenException("invalid token"));
            if(token.getExpirationDate().isBefore(LocalDateTime.now()))
                throw new InvalidTokenException("token is expired");
            if(token.getAppUser().isEnabled())
                throw new InvalidTokenException("user is already verified");

          return token.getAppUser();
        }
        throw new InvalidTokenException("invalid token");    }
}