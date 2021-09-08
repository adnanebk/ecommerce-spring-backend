package com.adnanbk.ecommerceang.services;

import com.adnanbk.ecommerceang.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.LoginUserDto;
import com.adnanbk.ecommerceang.models.AppUser;

public interface AuthService {
    JwtResponse handleLogin(LoginUserDto appUser);

    JwtResponse handleRegister(AppUser user);

    boolean verify(String token);

    void sendEmailConfirmation(String user);

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String userName);

    JwtResponse refreshNewToken(String refreshToken);

    JwtResponse generateTokens(AppUser user);
}
