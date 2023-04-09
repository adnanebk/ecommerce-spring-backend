package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.*;
import com.adnanbk.ecommerce.events.OnRegistrationCompleteEvent;
import com.adnanbk.ecommerce.events.RegistrationEventSource;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.SocialService;
import com.adnanbk.ecommerce.services.imp.FacebookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthService authService;
    private final SocialService googleService;
    private final FacebookService facebookService;

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "register")
    @ApiOperation(value = "register a new user", response = AuthDataDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDataDto register(@RequestBody @Valid UserInputDto userDto) {
        return Optional.of(userDto)
                .map(authService::handleRegister)
                .map(authDataDto-> {
                    var user  = authDataDto.appUser();
                    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(new RegistrationEventSource(user.getFirstName(),user.getEmail())));
                    return authDataDto;
                })
                .orElseThrow();
    }

    @PostMapping("refresh-token")
    @ApiOperation(value = "generate new refresh token")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDataDto refreshNewToken(@RequestBody String refreshToken) {
             return  this.authService.refreshNewToken(refreshToken);
    }
    @PostMapping("login")
    @ApiOperation(value = "authenticate a user")
    public AuthDataDto login(@RequestBody @Valid LoginUserDto appUser) {
        return authService.handleLogin(appUser);
    }

    @PostMapping("login/google")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "authenticate a google user")
    public AuthDataDto googleLogin(@RequestBody @Valid SocialLoginDto socialLoginDto) {
        return authService.handleSocialLogin(socialLoginDto,googleService);

    }

    @PostMapping("login/facebook")
    @ApiOperation(value = "authenticate a facebook user")
    public AuthDataDto facebookLogin(@RequestBody @Valid SocialLoginDto socialLoginDto) {
        return authService.handleSocialLogin(socialLoginDto,facebookService);
    }


    @PatchMapping("/change-password")
    @ApiOperation(value = "change the user password")
    public void changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto) {
        this.authService.changePassword(changeUserPasswordDto);
    }

    @PatchMapping("/send-confirmation")
    @ApiOperation(value = "send a confirmation token to the user email")
    public void sendEmailConfirmation() {
        var user = authService.getAuthenticatedUser();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(new RegistrationEventSource(user.getFirstName(),user.getEmail())));
    }


}
