package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.services.SocialService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class GoogleService implements SocialService {


    private final GoogleIdTokenVerifier googleVerifier;


    @Override
    public boolean verify(String authToken) {
        return Optional.ofNullable(authToken)
                .map(this::doVerify)
                .orElse(false);
    }

    private boolean doVerify(String token) {
        try {
            return Optional.ofNullable(googleVerifier.verify(token)).isPresent();

        } catch (GeneralSecurityException | IOException e) {
            return false;
        }
    }

}
