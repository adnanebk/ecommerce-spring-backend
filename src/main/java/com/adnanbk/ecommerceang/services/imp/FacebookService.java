package com.adnanbk.ecommerceang.services.imp;


import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.UserDto;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import com.adnanbk.ecommerceang.services.AuthService;
import com.adnanbk.ecommerceang.services.SocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FacebookService implements SocialService {

    private final RestTemplate restTemplate;

    @Value("${facebook.clientId}")
    private String clientId;
    @Value("${facebook.clientSecret}")
    private String clientSecret;
    private  String apiInspector ="https://graph.facebook.com/debug_token";




    public UserDto verify(JwtResponse jwtResponse)  {
        String token=jwtResponse.getToken();
        UserDto user=jwtResponse.getAppUser();
        if(token==null)
            throw new BadCredentialsException("Invalid credentials");
        String accessToken=clientId+'|'+clientSecret;
        ResponseEntity<String> response=  restTemplate.getForEntity(apiInspector +"?input_token={token}&access_token={app-token}"
                        ,String.class,token,accessToken);

            if (response.getStatusCode().is2xxSuccessful()) {
              return user;

            } else {
                System.out.println("Invalid ID token.");
                throw new BadCredentialsException("Invalid credentials");
            }

    }

}
