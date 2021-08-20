package com.adnanbk.ecommerceang.Controllers;


import com.adnanbk.ecommerceang.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.LoginUserDto;
import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.services.AuthService;
import com.adnanbk.ecommerceang.services.SocialService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AuthController {


    private AuthService authService;
    private SocialService googleService;
    private SocialService facebookService;
    @Value("${front.url}")
    private String frontUrl;

    public AuthController(AuthService authService, SocialService googleService, SocialService facebookService) {
        this.googleService = googleService;
        this.facebookService = facebookService;
        this.authService = authService;
    }


    @PostMapping(value = "/register")
    @ApiOperation(value = "register new user",response =JwtResponse.class )
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse create( @RequestBody @Valid AppUser user)   {
        user.setEnabled(false);
        return  authService.handleRegister(user);

    }
    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody @Valid LoginUserDto appUser) {
    return authService.handleLogin(appUser);
}

@PostMapping("/google")
public JwtResponse googleLogin(@RequestBody @Valid JwtResponse jwtResponse){
            return googleService.verify(jwtResponse);

}
    @PostMapping("/facebook")
    public JwtResponse facebookLogin(@RequestBody @Valid JwtResponse jwtResponse) {
        return facebookService.verify(jwtResponse);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
     boolean isVerified= authService.verify(token);
     if(isVerified)
         return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(frontUrl+"?verified=true")).build();

       return ResponseEntity.badRequest().body("Sorry, we could not verify account. It maybe already verified,or verification code is incorrect.");

    }
    @PostMapping("/appUsers/confirm")
    public ResponseEntity<?> sendEmailConfirmation(@RequestBody String email) {
        authService.sendEmailConfirmation(email);
        return ResponseEntity.ok().build();
     }
    @PostMapping("/appUsers/change-password")
    public ResponseEntity<?> changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto,Principal principal) {
      this.authService.changePassword(changeUserPasswordDto,principal.getName());
        return ResponseEntity.ok().build();
    }

}
