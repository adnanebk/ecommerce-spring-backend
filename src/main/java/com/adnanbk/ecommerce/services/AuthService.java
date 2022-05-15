package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.models.AppUser;

public interface AuthService {

    JwtDto handleLoginWithGoogle(JwtDto jwtDto);
    JwtDto handleLoginWithFacebook(JwtDto jwtDto);
    JwtDto handleLogin(LoginUserDto appUser);
    JwtDto handleRegister(AppUser user);
    JwtDto refreshNewToken(String refreshToken);
    String enableUser(String token);

    AppUser getAuthenticatedUser();

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto);
}
