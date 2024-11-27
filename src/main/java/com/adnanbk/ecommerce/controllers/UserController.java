package com.adnanbk.ecommerce.controllers;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.mappers.UserMapper;
import com.adnanbk.ecommerce.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PatchMapping("/current")
    @ApiOperation(value = "change authenticated user information")
    public void changeUserInformation(@RequestBody @Valid UserInputDto userDto, Principal principal) {
        Optional.of(userDto)
                .map(userMapper::toEntity)
                .ifPresent(user -> userService.update(user, principal.getName()));
    }

    @PatchMapping("/current/upload-image")
    @ApiOperation(value = "add or update a user image", notes = "this endpoint add or update  a user image and return its url", response = String.class)
    @ResponseStatus(HttpStatus.CREATED)
    public ImageDto updateUserImage(@RequestPart("image") MultipartFile file, Principal principal) {
        return userService.changeUserImage(file, principal.getName());
    }

    @PostMapping("/current/enable")
    @ApiOperation(value = "enable the user with the token sent to his email")
    public void enableUser(@RequestBody String token, Principal principal) {
        userService.enableUser(token, principal.getName());
    }
}