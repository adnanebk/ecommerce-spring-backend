package com.adnanbk.ecommerceang.Controllers;


import com.adnanbk.ecommerceang.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.LoginUserDto;
import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.services.AuthService;
import com.adnanbk.ecommerceang.services.SocialService;
import com.adnanbk.ecommerceang.services.imp.GoogleService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @Value("${front.url}")
    private String frontUrl;



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
            return authService.handleLoginWithGoogle(jwtResponse);

}
    @PostMapping("/facebook")
    public JwtResponse facebookLogin(@RequestBody @Valid JwtResponse jwtResponse) {
        return authService.handleLoginWithFacebook(jwtResponse);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam String token) {
     boolean isVerified= authService.verify(token);
     if(isVerified)
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(frontUrl+"?verified=true")).build();

        return ResponseEntity.badRequest().body("Sorry, we could not verify account. It maybe already verified,or verification code is incorrect.");

    }
    @PostMapping("/confirm")
    public ResponseEntity<?> sendEmailConfirmation(@RequestBody String email) {
        authService.sendEmailConfirmation(email);
        return ResponseEntity.ok().build();
     }
    @PostMapping("/appUsers/change-password")
    public ResponseEntity<?> changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto,Principal principal) {
      this.authService.changePassword(changeUserPasswordDto,principal.getName());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshNewToken(@RequestBody  String refreshToken) {
      var jwtResponse=  this.authService.refreshNewToken(refreshToken);
        return ResponseEntity.ok().body(jwtResponse);
    }
}
