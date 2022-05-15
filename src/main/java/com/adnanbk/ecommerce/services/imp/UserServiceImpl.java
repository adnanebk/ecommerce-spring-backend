package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.UserInputDto;
import com.adnanbk.ecommerce.exceptions.InvalidPasswordException;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.UserService;
import com.adnanbk.ecommerce.utils.ErrorMessagesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final AuthService authService;
    private  final ErrorMessagesUtil messagesUtil;
    private final PasswordEncoder passwordEncode;


    @Override
    public ImageDto changeUserImage(String fileName) {
        userRepo.updateImage(authService.getAuthenticatedUser().getEmail(),fileName);
        return new ImageDto(fileName);
    }

 /*   @Override
    public AppUser getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow();
    }*/
    @Override
    public void changePassword(ChangeUserPasswordDto changeUserPasswordDto) {
             AppUser user = authService.getAuthenticatedUser();
            if (!passwordEncode.matches(changeUserPasswordDto.getCurrentPassword(), user.getPassword()))
                throw new InvalidPasswordException(messagesUtil.getDefaultMessage("error.invalid-password"));
            var newPassword = passwordEncode.encode(changeUserPasswordDto.getNewPassword());
            userRepo.updatePassword(user.getId(),newPassword);


    }

    @Override
    public void update(UserInputDto user, Long id) {
        userRepo.update(user.getFirstName(),user.getLastName(),user.getEmail(),user.getCity(),user.getCountry(),user.getStreet(),id);
    }


}
