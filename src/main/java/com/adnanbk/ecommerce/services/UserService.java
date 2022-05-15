package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserInputDto;

public interface UserService {
    ImageDto changeUserImage(String fileName);

    void update(UserInputDto user, Long id);
}
