package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.*;
import com.adnanbk.ecommerce.mappers.UserMapper;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.EmailSenderService;
import com.adnanbk.ecommerce.services.FileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {


    private final AuthService authService;
    private final EmailSenderService emailSenderService;
    private final FileService imageService;
    private final UserMapper userMapper;
    private final Validator validator;



    private String getRootUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/api").toUriString();
    }


    @PostMapping(value = "/register")
    @ApiOperation(value = "register a new user", response = JwtDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto register(@RequestBody @Valid UserDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toEntity)
                .map(authService::handleRegister)
                .map(user-> {
                    emailSenderService.sendEmailConfirmation(getRootUrl(), userDto.getEmail());
                    return user;
                })
                .orElseThrow();


    }

    @PostMapping("/login")
    @ApiOperation(value = "authenticate a user")
    public JwtDto login(@RequestBody @Valid LoginUserDto appUser) {
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

    @GetMapping("/enable")
    @ApiOperation(value = "enable the user with the token sent to his email")
    public ResponseEntity<String> enableUser(@RequestParam String token) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(emailSenderService.enableUser(token))).build();
    }

    @PostMapping("/refresh-token")
    @ApiOperation(value = "generate new refresh token")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto refreshNewToken(@RequestBody String refreshToken) {
        return this.authService.refreshNewToken(refreshToken);

    }

    @PatchMapping("/user/confirm")
    @ApiOperation(value = "send a confirmation token to the user email")
    public void sendEmailConfirmation(@RequestBody String email) {
        emailSenderService.sendEmailConfirmation(getRootUrl(),email);
    }

    @PostMapping("/user/change-password")
    @ApiOperation(value = "change the user password")
    public void changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto, Principal principal) {
        this.authService.changePassword(changeUserPasswordDto, principal.getName());
    }

    @PatchMapping(value = "/user/upload-image")
    @ApiOperation(value = "add or update a user image", notes = "this endpoint add or update  a user image and return its url", response = String.class)
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ImageDto> updateUserImage(@RequestPart("image") MultipartFile file, Principal principal) {
        return this.imageService.upload(file).thenApplyAsync(fileName-> this.authService.changeUserImage(fileName,principal.getName()));
    }


}
