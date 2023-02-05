package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.dto.SocialLoginDto;
import com.adnanbk.ecommerce.models.AppUser;

public interface AuthService {

    AppUser handleSocialLogin(SocialLoginDto user, SocialService socialService);

    AppUser handleLogin(LoginUserDto appUser);
    AppUser handleRegister(AppUser user);


    AppUser getAuthenticatedUser();

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto);

    AppUser getUserByEmail(String email);
}
