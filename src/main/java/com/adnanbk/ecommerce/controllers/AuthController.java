package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.*;
import com.adnanbk.ecommerce.events.OnRegistrationCompleteEvent;
import com.adnanbk.ecommerce.mappers.UserMapper;
import com.adnanbk.ecommerce.services.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    private String getRootUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api").toUriString();
    }


    @PostMapping(value = "register")
    @ApiOperation(value = "register a new user", response = AuthDataDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDataDto register(@RequestBody @Valid UserInputDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toEntity)
                .map(user-> {
                    AuthDataDto authDataDto =  authService.handleRegister(user);
                    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(getRootUrl(),user));
                    return authDataDto;
                })
                .orElseThrow();


    }

    @PostMapping("login")
    @ApiOperation(value = "authenticate a user")
    public AuthDataDto login(@RequestBody @Valid LoginUserDto appUser) {
        return authService.handleLogin(appUser);
    }

    @PostMapping("login/google")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "authenticate a google user")
    public AuthDataDto googleLogin(@RequestBody @Valid AuthDataDto authDataDto) {
        return authService.handleLoginWithGoogle(authDataDto);

    }

    @PostMapping("login/facebook")
    @ApiOperation(value = "authenticate a facebook user")
    public AuthDataDto facebookLogin(@RequestBody @Valid AuthDataDto authDataDto) {
        return authService.handleLoginWithFacebook(authDataDto);
    }

    @GetMapping("enable")
    @ApiOperation(value = "enable the user with the token sent to his email")
    public ResponseEntity<String> enableUser(@RequestParam String token) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(authService.enableUser(token))).build();
    }
    @PostMapping("refresh-token")
    @ApiOperation(value = "generate new refresh token")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDataDto refreshNewToken(@RequestBody String refreshToken) {
        return this.authService.refreshJwtToken(refreshToken);
    }
    @GetMapping("user")
    @ApiOperation(value = "get authenticated user details")
    public UserOutputDto getAuthUser() {
        return userMapper.toDto(authService.getAuthenticatedUser());
    }

    @PatchMapping("user/change-password")
    @ApiOperation(value = "change the user password")
    public void changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto) {
        this.authService.changePassword(changeUserPasswordDto);
    }

    @PatchMapping("user/send-confirmation")
    @ApiOperation(value = "send a confirmation token to the user email")
    public void sendEmailConfirmation() {
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(getRootUrl(),authService.getAuthenticatedUser()));
    }




}
