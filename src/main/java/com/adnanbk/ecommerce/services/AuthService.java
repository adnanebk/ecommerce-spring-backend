package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.ImageDto;
import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.models.AppUser;

public interface AuthService {

    JwtDto handleLoginWithGoogle(JwtDto jwtDto);

    JwtDto handleLoginWithFacebook(JwtDto jwtDto);

    JwtDto handleLogin(LoginUserDto appUser);

    JwtDto handleRegister(AppUser user);

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String email);

    JwtDto refreshNewToken(String refreshToken);


    ImageDto changeUserImage(String fileName, String email);
}
