package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.LoginUserDto;
import com.adnanbk.ecommerceang.dto.UserDto;
import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import com.adnanbk.ecommerceang.services.AuthService;
import com.adnanbk.ecommerceang.services.SocialService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class GoogleService implements SocialService {


   private final GoogleIdTokenVerifier googleverifier;
   private final UserRepo userRepo;
   private final AuthService authService;

   @Value("${social.password}")
   private String password;


   @Override
   public JwtResponse verify(JwtResponse jwtResponse)  {
      String token=jwtResponse.getToken();
      UserDto user=jwtResponse.getAppUser();
      if(token==null)
         throw new BadCredentialsException("Invalid credentials");
      GoogleIdToken idToken;
      try {
         idToken = googleverifier.verify(token);
      if (idToken != null) {
       //  GoogleIdToken.Payload payload = idToken.getPayload();
         if(!userRepo.existsByUserName(user.getUserName())){
         AppUser appUser=new AppUser(user.getUserName(),user.getEmail(),user.getFirstName(),user.getLastName(),password);
            appUser.setEnabled(true);
         return authService.handleRegister(appUser);
         }
         else
         {
            LoginUserDto loginUserDto=new LoginUserDto(user.getUserName(),password);
            return authService.handleLogin(loginUserDto);
         }

      } else {
         System.out.println("Invalid ID token.");
         throw new BadCredentialsException("Invalid credentials");
      }
      } catch (GeneralSecurityException | IOException e) {
        throw new BadCredentialsException("Invalid credentials");
      }
   }

}
