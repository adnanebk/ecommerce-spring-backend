package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.events.OnRegistrationCompleteEvent;
import com.adnanbk.ecommerce.models.ConfirmationToken;
import com.adnanbk.ecommerce.reposetories.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationListener {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepo;
    @Value("${spring.mail.name}")
    private String name;




    @Async
    @EventListener
    public void sendEmailConfirmation(OnRegistrationCompleteEvent event) {
        var user = event.getUser();
        String  rootUrl = event.getUrl();
        String destAddress = user.getEmail();
        String token=UUID.randomUUID().toString();

        confirmationTokenRepo.save(new ConfirmationToken(token, LocalDate.now().plusDays(1),user));

        String verifyURL = rootUrl + "/auth/enable?token=" +token;
        String subject = "Please verify your registration";
        String content = String.format("Dear %s,<br>Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"%s\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br> %s.", user.getFirstName(), verifyURL, name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(user.getEmail(), name);

            helper.setTo(destAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("We could not verify the email");
        }
        javaMailSender.send(message);
    }



}
