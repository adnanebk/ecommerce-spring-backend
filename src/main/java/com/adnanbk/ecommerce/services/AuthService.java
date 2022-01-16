package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.JwtResponse;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.models.AppUser;

public interface AuthService {

    JwtResponse handleLoginWithGoogle(JwtResponse jwtResponse);

    JwtResponse handleLoginWithFacebook(JwtResponse jwtResponse);

    JwtResponse handleLogin(LoginUserDto appUser);

    JwtResponse handleRegister(AppUser user);

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String userName);

    JwtResponse refreshNewToken(String refreshToken);


}
