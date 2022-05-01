package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.exceptions.InvalidPasswordException;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.UserService;
import com.adnanbk.ecommerce.utils.ErrorMessagesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {



    private final UserRepo userRepo;
    private  final ErrorMessagesUtil messagesUtil;
    private final PasswordEncoder passwordEncode;
    @Override
    public ImageDto changeUserImage(String fileName, String email) {
        userRepo.updateImage(email,fileName);
        return userRepo.findByEmail(email).map(user ->new ImageDto(user.getImageUrl())).orElseThrow();
    }

    @Override
    public Optional<AppUser> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    @Override
    public void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String email) {
        userRepo.findByEmail(email).ifPresent(user->{
            if (!passwordEncode.matches(changeUserPasswordDto.getCurrentPassword(), user.getPassword()))
                throw new InvalidPasswordException(messagesUtil.getDefaultMessage("error.invalid-password"),"currentPassword");
            var newPassword = passwordEncode.encode(changeUserPasswordDto.getNewPassword());
            userRepo.updatePassword(user.getId(),newPassword);
        });

    }

    @Override
    @Transactional
    public AppUser update(AppUser user,Long id) {
        return userRepo.findById(id)
                .map(us-> {
                    // update only the needed fields
                    us.setEmail(user.getEmail());
                    us.setFirstName(user.getFirstName());
                    us.setCity(user.getCity());
                    us.setCountry(user.getCountry());
                    us.setStreet(user.getStreet());
                   return userRepo.save(us);
                }).orElseThrow();

    }


}
