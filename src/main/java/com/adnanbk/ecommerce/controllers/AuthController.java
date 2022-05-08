package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.dto.UserInfoDto;
import com.adnanbk.ecommerce.mappers.UserMapper;
import com.adnanbk.ecommerce.services.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {


    private final AuthService authService;
    private final UserMapper userMapper;


    private String getRootUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api").toUriString();
    }


    @PostMapping(value = "register")
    @ApiOperation(value = "register a new user", response = JwtDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto register(@RequestBody @Valid UserInfoDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toEntity)
                .map(user->authService.handleRegister(getRootUrl(),user))
                .orElseThrow();


    }

    @PostMapping("login")
    @ApiOperation(value = "authenticate a user")
    public JwtDto login(@RequestBody @Valid LoginUserDto appUser) {
        return authService.handleLogin(appUser);
    }

    @PostMapping("login/google")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "authenticate a google user")
    public JwtDto googleLogin(@RequestBody @Valid JwtDto jwtDto) {
        return authService.handleLoginWithGoogle(jwtDto);

    }

    @PostMapping("login/facebook")
    @ApiOperation(value = "authenticate a facebook user")
    public JwtDto facebookLogin(@RequestBody @Valid JwtDto jwtDto) {
        return authService.handleLoginWithFacebook(jwtDto);
    }

    @GetMapping("enable")
    @ApiOperation(value = "enable the user with the token sent to his email")
    public ResponseEntity<String> enableUser(@RequestParam String token) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(authService.enableUser(token))).build();
    }

    @PostMapping("refresh-token")
    @ApiOperation(value = "generate new refresh token")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto refreshNewToken(@RequestBody String refreshToken) {
        return this.authService.refreshNewToken(refreshToken);

    }

    @PatchMapping("confirm")
    @ApiOperation(value = "send a confirmation token to the user email")
    public void sendEmailConfirmation(@RequestBody String email) {
        authService.sendEmailConfirmation(getRootUrl(),email);
    }




}
