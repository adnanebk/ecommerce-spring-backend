package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.UserService;
import com.adnanbk.ecommerce.utils.ConfirmationTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;




    @Override
    public ImageDto changeUserImage(String fileName, String email) {
        userRepo.updateImage(email,fileName);
        return new ImageDto(fileName);
    }


    @Override
    public void update(AppUser user, String email) {
        userRepo.findByEmail(email).ifPresent(savedUser->{
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
        ConfirmationTokenUtil.verifyToken(email,token);
        userRepo.enableUser(email,true);
    }



}
