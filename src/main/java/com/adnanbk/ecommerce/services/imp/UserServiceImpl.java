package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.UserService;
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
    public void update(UserInputDto user, Long id) {
        userRepo.update(user.getFirstName(),user.getLastName(),user.getEmail(),user.getCity(),user.getCountry(),user.getStreet(),id);
    }


}
