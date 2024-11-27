package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.FileService;
import com.adnanbk.ecommerce.services.UserService;
import com.adnanbk.ecommerce.utils.ConfirmationTokenUtil;
import com.adnanbk.ecommerce.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final FileService fileService;
    private final ImageUtil imageUtil;

    @Override
    public ImageDto changeUserImage(MultipartFile imageFile, String email) {
        String imageUrl = fileService.upload(imageFile);
        userRepo.updateImage(email, imageUrl);
        return new ImageDto(imageUtil.toImageUrl(imageUrl));
    }


    @Override
    public void update(AppUser user, String email) {
        userRepo.findByEmail(email).ifPresent(savedUser -> {
            mapUser(user, savedUser);
            userRepo.save(savedUser);

        });
    }

    private static void mapUser(AppUser src, AppUser target) {
        target.setEmail(src.getEmail());
        target.setStreet(src.getStreet());
        target.setCity(src.getCity());
        target.setCountry(src.getCountry());
        target.setFirstName(src.getFirstName());
        target.setLastName(src.getLastName());
    }

    @Override
    public void enableUser(String token, String email) {
        ConfirmationTokenUtil.verifyToken(email, token);
        userRepo.enableUser(email, true);
    }


}
