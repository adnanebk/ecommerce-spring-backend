package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.services.SocialService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;


@RequiredArgsConstructor
@Service
public class GoogleService implements SocialService {


    private final GoogleIdTokenVerifier googleverifier;


    @Override
    public boolean verify(JwtDto jwtDto) {
        String token = jwtDto.getToken();
        if (token == null) {
            return false;
        }

        return doVerify(token);

    }

    private boolean doVerify(String token) {
        try {
            GoogleIdToken idToken = googleverifier.verify(token);

            return (idToken != null);


        } catch (GeneralSecurityException | IOException e) {
            return false;
        }
    }

}
