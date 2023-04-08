package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.*;
import com.adnanbk.ecommerce.models.AppUser;

public interface AuthService {

    AuthDataDto handleSocialLogin(SocialLoginDto user, SocialService socialService);

    AuthDataDto handleLogin(LoginUserDto appUser);
    AuthDataDto handleRegister(UserInputDto userInputDto);


    AppUser getAuthenticatedUser();

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto);


    AuthDataDto refreshNewToken(String refreshToken);
}
