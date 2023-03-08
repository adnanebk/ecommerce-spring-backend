package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.models.AppUser;

public interface UserService {
    ImageDto changeUserImage(String fileName, String email);


    void update(AppUser user, String email);

    void enableUser(String token, String email);
}
