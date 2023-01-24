package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.AuthDataDto;
import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.dto.SocialLoginDto;
import com.adnanbk.ecommerce.models.AppUser;

public interface AuthService {

    AuthDataDto handleSocialLogin(SocialLoginDto user, SocialService socialService);

    AuthDataDto handleLogin(LoginUserDto appUser);
    AuthDataDto handleRegister(AppUser user);

    AuthDataDto refreshJwtToken(String refreshToken);

    AppUser getAuthenticatedUser();

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto);
}
