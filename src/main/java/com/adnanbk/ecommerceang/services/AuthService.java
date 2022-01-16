package com.adnanbk.ecommerceang.services;

import com.adnanbk.ecommerceang.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.LoginUserDto;
import com.adnanbk.ecommerceang.models.AppUser;

import java.security.SecureRandom;

public interface AuthService {

    JwtResponse handleLoginWithGoogle(JwtResponse jwtResponse);

    JwtResponse handleLoginWithFacebook(JwtResponse jwtResponse);

    JwtResponse handleLogin(LoginUserDto appUser);

    JwtResponse handleRegister(AppUser user);

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String userName);

    JwtResponse refreshNewToken(String refreshToken);


}
