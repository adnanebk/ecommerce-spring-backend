package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.models.AppUser;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ImageDto changeUserImage(MultipartFile imageFile, String email);


    void update(AppUser user, String email);

    void enableUser(String token, String email);
}
