package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserDto;
import com.adnanbk.ecommerce.dto.UserInfoDto;
import com.adnanbk.ecommerce.mappers.UserMapper;
import com.adnanbk.ecommerce.services.FileService;
import com.adnanbk.ecommerce.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

private final UserService userService;
private final FileService imageService;
private final UserMapper userMapper;

    @GetMapping("email/{email}")
    @ApiOperation(value = "Get a user by its email")
    public UserDto getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(userMapper::toDto).orElseThrow();
    }

    @PatchMapping("change-password")
    @ApiOperation(value = "change the user password")
    public void changeUserPassword(@RequestBody @Valid ChangeUserPasswordDto changeUserPasswordDto, Principal principal) {
        this.userService.changePassword(changeUserPasswordDto, principal.getName());
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "change user information")
    public void changeUserInformation(@RequestBody @Valid UserInfoDto userDto, @PathVariable  Long id)
    {
        Optional.of(userDto)
                .ifPresent(user->userService.update(user,id));
    }
    @PatchMapping(value = "upload-image")
    @ApiOperation(value = "add or update a user image", notes = "this endpoint add or update  a user image and return its url", response = String.class)
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ImageDto> updateUserImage(@RequestPart("image") MultipartFile file, Principal principal) {
        return this.imageService.upload(file).thenApplyAsync(fileName-> this.userService.changeUserImage(fileName,principal.getName()));
    }
}


