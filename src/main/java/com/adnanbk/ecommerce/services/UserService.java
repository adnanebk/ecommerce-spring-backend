package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserInfoDto;
import com.adnanbk.ecommerce.models.AppUser;

import java.util.Optional;

public interface UserService {
    ImageDto changeUserImage(String fileName, String email);
    Optional<AppUser> getUserByEmail(String email);

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String email);

    AppUser update(UserInfoDto user, Long id);
}
