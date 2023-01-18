package com.adnanbk.ecommerce.events.listeners;

import com.adnanbk.ecommerce.events.OnRegistrationCompleteEvent;
import com.adnanbk.ecommerce.utils.ConfirmationTokenUtil;
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

@Component
@RequiredArgsConstructor
public class RegistrationListener {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.name}")
    private String name;

    @Value("${spring.mail.email}")
    private String email;

    @Async
    @EventListener
    public void sendEmailConfirmation(OnRegistrationCompleteEvent event) throws MessagingException, UnsupportedEncodingException {
        var user = event.getUser();
        String  rootUrl = event.getUrl();
        String destAddress = user.getEmail();
        String token =ConfirmationTokenUtil.setConfirmationTokenForUser(user).getToken();
        String verifyURL = rootUrl + "/auth/enable?token=" +token;
        String subject = "Please verify your registration";
        String content = String.format("Dear %s,<br>Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"%s\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br> %s.", user.getFirstName(), verifyURL, name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(email, name);
            helper.setTo(destAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
        javaMailSender.send(message);
    }



}
