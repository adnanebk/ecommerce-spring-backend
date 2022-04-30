package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.models.AppUser;

import java.util.Optional;

public interface AuthService {

    JwtDto handleLoginWithGoogle(JwtDto jwtDto);
    JwtDto handleLoginWithFacebook(JwtDto jwtDto);
    JwtDto handleLogin(LoginUserDto appUser);
    JwtDto handleRegister(String rootUrl, AppUser user);
    String enableUser(String token);
    void sendEmailConfirmation(String rootUrl,String email);

    JwtDto refreshNewToken(String refreshToken);
    void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String email);
    ImageDto changeUserImage(String fileName, String email);
    Optional<AppUser> getUserByEmail(String email);

}
