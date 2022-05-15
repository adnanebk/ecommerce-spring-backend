package com.adnanbk.ecommerce.controllers;


import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.services.FileService;
import com.adnanbk.ecommerce.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

private final UserService userService;
private final FileService imageService;


    @PatchMapping("/{id}")
    @ApiOperation(value = "change user information")
    public void changeUserInformation(@RequestBody @Valid UserInputDto userDto, @PathVariable  Long id)
    {
        Optional.of(userDto)
                .ifPresent(user->userService.update(user,id));
    }
    @PatchMapping(value = "upload-image")
    @ApiOperation(value = "add or update a user image", notes = "this endpoint add or update  a user image and return its url", response = String.class)
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ImageDto> updateUserImage(@RequestPart("image") MultipartFile file) {
        return this.imageService.upload(file).thenApplyAsync(this.userService::changeUserImage);
    }
}


