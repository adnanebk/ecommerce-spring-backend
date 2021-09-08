package com.adnanbk.ecommerceang.services.imp;


import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.LoginUserDto;
import com.adnanbk.ecommerceang.dto.UserDto;
import com.adnanbk.ecommerceang.models.AppUser;
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

    private final UserRepo userRepo;
    private final AuthService authService;
    private final RestTemplate restTemplate;

    @Value("${facebook.clientId}")
    private String clientId;
    @Value("${facebook.clientSecret}")
    private String clientSecret;
    private  String apiInspector ="https://graph.facebook.com/debug_token";




    public JwtResponse verify(JwtResponse jwtResponse)  {
        String token=jwtResponse.getToken();
        UserDto user=jwtResponse.getAppUser();
        if(token==null)
            throw new BadCredentialsException("Invalid credentials");
        String accessToken=clientId+'|'+clientSecret;
        ResponseEntity<String> response=  restTemplate.getForEntity(apiInspector +"?input_token={token}&access_token={app-token}"
                        ,String.class,token,accessToken);

            if (response.getStatusCode().is2xxSuccessful()) {
                AppUser appUser=userRepo.findByUserName(user.getUserName());
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

    }

}
