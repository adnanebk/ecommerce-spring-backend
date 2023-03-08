package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.*;
import com.adnanbk.ecommerce.events.OnRegistrationCompleteEvent;
import com.adnanbk.ecommerce.jwt.JwtTokenService;
import com.adnanbk.ecommerce.mappers.UserMapper;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.SocialService;
import com.adnanbk.ecommerce.services.imp.FacebookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {


    private final AuthService authService;
    private final SocialService googleService;
    private final FacebookService facebookService;
    private final JwtTokenService jwtTokenService;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "register")
    @ApiOperation(value = "register a new user", response = AuthDataDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDataDto register(@RequestBody @Valid UserInputDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toEntity)
                .map(authService::handleRegister)
                .map(user-> {
                    AuthDataDto authDataDto =  buildAuthData(user);
                    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(getRootUrl(),user));
                    return authDataDto;
                })
                .orElseThrow();
    }

    @PostMapping("refresh-token")
    @ApiOperation(value = "generate new refresh token")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDataDto refreshNewToken(@RequestBody String refreshToken) {
        String email  = this.jwtTokenService.validateTokenAndGetSubject(refreshToken);
              return   buildAuthData(this.authService.getUserByEmail(email));
    }
    @PostMapping("login")
    @ApiOperation(value = "authenticate a user")
    public AuthDataDto login(@RequestBody @Valid LoginUserDto appUser) {
        return buildAuthData(authService.handleLogin(appUser));
    }

    @PostMapping("login/google")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "authenticate a google user")
    public AuthDataDto googleLogin(@RequestBody @Valid SocialLoginDto socialLoginDto) {
        return buildAuthData(authService.handleSocialLogin(socialLoginDto,googleService));

    }

    @PostMapping("login/facebook")
    @ApiOperation(value = "authenticate a facebook user")
    public AuthDataDto facebookLogin(@RequestBody @Valid SocialLoginDto socialLoginDto) {
        return buildAuthData(authService.handleSocialLogin(socialLoginDto,facebookService));
    }

    @GetMapping("user-info")
    @ApiOperation(value = "get authenticated user details")
    public UserOutputDto getAuthUser() {
        return userMapper.toDto(authService.getAuthenticatedUser());
    }

    @PatchMapping("/change-password")
    @ApiOperation(value = "change the user password")
    public void changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto) {
        this.authService.changePassword(changeUserPasswordDto);
    }

    @PatchMapping("/send-confirmation")
    @ApiOperation(value = "send a confirmation token to the user email")
    public void sendEmailConfirmation() {
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(getRootUrl(),authService.getAuthenticatedUser()));
    }
    private String getRootUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api").toUriString();
    }

    private AuthDataDto buildAuthData(AppUser user) {
       return  Optional.of(user)
                 .map(userMapper::toDto).map(userDto-> {
                     var token = this.jwtTokenService.generateAccessToken(user.getEmail());
                     var refreshToken = this.jwtTokenService.generateRefreshToken(user.getEmail());
                     return new AuthDataDto(token.value(), refreshToken.value(), token.expirationDate(), userDto);
                 }).orElseThrow();
    }

}
