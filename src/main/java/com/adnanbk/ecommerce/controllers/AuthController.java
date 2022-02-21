package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.EmailSenderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {


    private final AuthService authService;
    private final EmailSenderService emailSenderService;



    private String getRootUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath(null).toUriString();
    }


    @PostMapping(value = "/register")
    @ApiOperation(value = "register a new user", response = JwtDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto create(@RequestBody @Valid AppUser user) {
        user.setEnabled(false);
        JwtDto jwtDto = authService.handleRegister(user);
        emailSenderService.sendEmailConfirmation(getRootUrl(),user.getEmail());
        return jwtDto;

    }

    @PostMapping("/login")
    @ApiOperation(value = "authenticate a user")
    public JwtDto authenticateUser(@RequestBody @Valid LoginUserDto appUser) {
        return authService.handleLogin(appUser);
    }

    @PostMapping("/login/google")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "authenticate a google user")
    public JwtDto googleLogin(@RequestBody @Valid JwtDto jwtDto) {
        return authService.handleLoginWithGoogle(jwtDto);

    }

    @PostMapping("/login/facebook")
    @ApiOperation(value = "authenticate a facebook user")
    public JwtDto facebookLogin(@RequestBody @Valid JwtDto jwtDto) {
        return authService.handleLoginWithFacebook(jwtDto);
    }

    @GetMapping("/appUsers/verify")
    @ApiOperation(value = "verify if the user is enabled")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(emailSenderService.verifyToken(token))).build();
    }

    @PostMapping("/appUsers/confirm")
    @ApiOperation(value = "send a confirmation token to the user email")
    public void sendEmailConfirmation(@RequestBody String email) {
        emailSenderService.sendEmailConfirmation(getRootUrl(),email);
    }

    @PostMapping("/appUsers/change-password")
    @ApiOperation(value = "change the user password")
    public void changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto, Principal principal) {
        this.authService.changePassword(changeUserPasswordDto, principal.getName());
    }


    @PostMapping("/appUsers/refresh-token")
    @ApiOperation(value = "generate new refresh token")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto refreshNewToken(@RequestBody String refreshToken) {
        return this.authService.refreshNewToken(refreshToken);

    }
}
