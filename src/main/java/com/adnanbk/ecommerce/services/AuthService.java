package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.models.AppUser;

public interface AuthService {

    JwtDto handleLoginWithGoogle(JwtDto jwtDto);
    JwtDto handleLoginWithFacebook(JwtDto jwtDto);
    JwtDto handleLogin(LoginUserDto appUser);
    JwtDto handleRegister(String rootUrl, AppUser user);
    JwtDto refreshNewToken(String refreshToken);
    void sendEmailConfirmation(String rootUrl, String email);
    String enableUser(String token);
}
