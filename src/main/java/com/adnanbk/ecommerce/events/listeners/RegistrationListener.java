package com.adnanbk.ecommerce.events.listeners;

import com.adnanbk.ecommerce.events.OnRegistrationCompleteEvent;
import com.adnanbk.ecommerce.utils.ConfirmationTokenUtil;
import com.adnanbk.ecommerce.utils.StringUtil;
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
    private String senderName;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Async
    @EventListener
    public void sendEmailConfirmation(OnRegistrationCompleteEvent event) throws MessagingException, UnsupportedEncodingException {
        var eventSource = event.getEventSource();
        String destAddress = eventSource.email();
        String confirmCode = StringUtil.generateCode(5);
        ConfirmationTokenUtil.seTokenForUser(eventSource.email(),confirmCode);
        String subject = "Please verify your registration";
        String content = String.format("""
                Dear %s,<br>Please use this code to active your account :<br>
                <strong>%s</strong><br>
                 Thank you,<br>""",eventSource.firstName(),confirmCode);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(senderEmail, senderName);
            helper.setTo(destAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
        javaMailSender.send(message);
    }



}
