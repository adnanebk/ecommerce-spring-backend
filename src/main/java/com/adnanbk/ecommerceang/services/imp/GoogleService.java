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
         var appUser = userRepo.findByUserName(user.getUserName());
         if(appUser==null){
           appUser=new AppUser(user.getUserName(),user.getEmail(),user.getFirstName(),user.getLastName(),generateRandomPassword(6));
           appUser.setEnabled(true);
           var resp= authService.handleRegister(appUser);
           resp.getAppUser().setIsSocial(true);
           return resp;
         }
         else
         {
            LoginUserDto loginUserDto=new LoginUserDto(user.getUserName(),appUser.getPassword());
            var resp= authService.handleLogin(loginUserDto);
            resp.getAppUser().setIsSocial(true);
            return resp;
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
