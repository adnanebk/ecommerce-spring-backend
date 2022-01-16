package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.InvalidTokenException;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.models.ConfirmationToken;
import com.adnanbk.ecommerce.reposetories.ConfirmationTokenRepository;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImp implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepo;
    private final UserRepo userRepo;
    @Value("${spring.mail.name}")
    private String name;
    @Value("${spring.mail.email}")
    private String email;
    @Value("${api.url}")
    private String url;


    @Async
    @Override
    public void sendEmailConfirmation(String email) {

        AppUser user = userRepo.findByEmail(email).orElseThrow(() -> new BadCredentialsException("could not found this email"));


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

    @Override
    public void verifyToken(String _token) {
        if (_token != null && !_token.isEmpty()) {
            var token = confirmationTokenRepo.findById(_token)
                    .orElseThrow(() -> new InvalidTokenException("invalid token"));
            if (token.getExpirationDate().isBefore(LocalDateTime.now()))
                throw new InvalidTokenException("token is expired");
            if (token.getAppUser().isEnabled())
                throw new InvalidTokenException("user is already verified");

            AppUser user = token.getAppUser();

            if (user == null)
                throw new InvalidTokenException("Sorry, we could not verify account. It maybe already verified,or verification code is incorrect.");

            user.setEnabled(true);
            userRepo.save(user);


        }
        throw new InvalidTokenException("invalid token");
    }
}