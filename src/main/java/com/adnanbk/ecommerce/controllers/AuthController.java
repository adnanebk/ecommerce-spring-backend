package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.JwtResponse;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.EmailSenderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final EmailSenderService emailSenderService;


    @Value("${front.url}")
    private String frontUrl;


    @PostMapping(value = "/register")
    @ApiOperation(value = "register new user", response = JwtResponse.class)
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse create(@RequestBody @Valid AppUser user) {
        user.setEnabled(false);
        JwtResponse jwtResponse = authService.handleRegister(user);
        emailSenderService.sendEmailConfirmation(user.getEmail());
        return jwtResponse;

    }

    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody @Valid LoginUserDto appUser) {
        return authService.handleLogin(appUser);
    }

    @PostMapping("/google")
    public JwtResponse googleLogin(@RequestBody @Valid JwtResponse jwtResponse) {
        return authService.handleLoginWithGoogle(jwtResponse);

    }

    @PostMapping("/facebook")
    public JwtResponse facebookLogin(@RequestBody @Valid JwtResponse jwtResponse) {
        return authService.handleLoginWithFacebook(jwtResponse);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        emailSenderService.verifyToken(token);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(frontUrl + "?verified=true")).build();


    }

    @PostMapping("/confirm")
    public ResponseEntity<String> sendEmailConfirmation(@RequestBody String email) {
        emailSenderService.sendEmailConfirmation(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/appUsers/change-password")
    public ResponseEntity<String> changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto, Principal principal) {
        this.authService.changePassword(changeUserPasswordDto, principal.getName());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshNewToken(@RequestBody String refreshToken) {
        var jwtResponse = this.authService.refreshNewToken(refreshToken);
        return ResponseEntity.ok().body(jwtResponse);
    }
}
