package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.exceptions.InvalidTokenException;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.UserService;
import com.adnanbk.ecommerce.utils.ConfirmationTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Value("${front.url}")
    private String frontUrl;


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
    public String enableUser(String token) {
        var user = verifyConfirmationTokenAndGetUser(token);
        userRepo.enableUser(user.getId(),true);
        return frontUrl + "?verified=true";
    }
    private AppUser verifyConfirmationTokenAndGetUser(String token) {
        return Optional.ofNullable(ConfirmationTokenUtil.getConfirmationToken(token))
                .map(confirmationToken->{
                            if (confirmationToken.getExpirationDate().isBefore(LocalDate.now()))
                                throw new InvalidTokenException("token is expired");
                            if(confirmationToken.getAppUser().isEnabled())
                                throw new InvalidTokenException("token is already enabled");
                            return confirmationToken.getAppUser();
                        }
                ).orElseThrow(()->new InvalidTokenException("the token is not found"));
    }


}
