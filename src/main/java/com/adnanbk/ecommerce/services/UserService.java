package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserInputDto;

public interface UserService {
    ImageDto changeUserImage(String fileName);

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto);

    void update(UserInputDto user, Long id);
}
