package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.services.SocialService;
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
   public boolean verify(JwtResponse jwtResponse)  {
      String token=jwtResponse.getToken();
      if(token!=null)
      try {
         GoogleIdToken  idToken = googleverifier.verify(token);
         //  GoogleIdToken.Payload payload = idToken.getPayload();

         return (idToken != null);


      } catch (GeneralSecurityException | IOException e) {
        return false;
      }
      return false;
   }

}
