package com.adnanbk.ecommerce.controllers;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.mappers.UserMapper;
import com.adnanbk.ecommerce.services.FileService;
import com.adnanbk.ecommerce.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

private final UserService userService;
private final UserMapper userMapper;
private final FileService imageService;

    @PatchMapping("/users/current")
    @ApiOperation(value = "change authenticated user information")
    public void changeUserInformation(@RequestBody @Valid UserInputDto userDto, Principal  principal)
    {
        Optional.of(userDto)
                .map(userMapper::toEntity)
                .ifPresent(user->userService.update(user,principal.getName()));
    }
    @PatchMapping("/users/current/upload-image")
    @ApiOperation(value = "add or update a user image", notes = "this endpoint add or update  a user image and return its url", response = String.class)
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ImageDto> updateUserImage(@RequestPart("image") MultipartFile file, Principal principal) {
        return this.imageService.upload(file).thenApplyAsync(fileName->userService.changeUserImage(fileName,principal.getName()));
    }

    @GetMapping("/user/enable")
    @ApiOperation(value = "enable the user with the token sent to his email")
    public ResponseEntity<String> enableUser(@RequestParam String token) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(userService.enableUser(token))).build();
    }
}