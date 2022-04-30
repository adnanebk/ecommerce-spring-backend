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
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImp implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepo;
    private final UserRepo userRepo;
    @Value("${spring.mail.name}")
    private String name;
    @Value("${front.url}")
    private String frontUrl;


    @Async
    @Override
    public void sendEmailConfirmation(String rooUrl,String email) {

        AppUser user = userRepo.findByEmail(email).orElseThrow(() -> new BadCredentialsException("could not found this email"));

        String destAddress = user.getEmail();
        String token=UUID.randomUUID().toString();

        confirmationTokenRepo.save(new ConfirmationToken(token, LocalDate.now().plusDays(1),user));

        String verifyURL = rooUrl + "/auth/enable?token=" +token;
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
            throw new RuntimeException("We could not verify the email");
        }
        javaMailSender.send(message);
    }

    @Override
    public String enableUser(String token) {
       var user = verifyAngGetTokenUser(token);
            userRepo.enableUser(user.getId(),true);
            return frontUrl + "?verified=true";


    }

    private AppUser verifyAngGetTokenUser(String token) {

        if (StringUtils.hasLength(token)) {
            var confirmationToken = confirmationTokenRepo.findById(token)
                    .orElseThrow(()->new InvalidTokenException("the token is not found"));
            if (confirmationToken.getExpirationDate().isAfter(LocalDate.now())
                      && !confirmationToken.getAppUser().isEnabled()){
                return confirmationToken.getAppUser();
            }
        }
        throw new InvalidTokenException("token is invalid or it has been expired");
    }
}
