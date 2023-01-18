package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.AuthDataDto;
import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.models.AppUser;

public interface AuthService {

    AuthDataDto handleLoginWithGoogle(AuthDataDto authDataDto);
    AuthDataDto handleLoginWithFacebook(AuthDataDto authDataDto);
    AuthDataDto handleLogin(LoginUserDto appUser);
    AuthDataDto handleRegister(AppUser user);
    AuthDataDto refreshJwtToken(String refreshToken);
    String enableUser(String token);

    AppUser getAuthenticatedUser();

    void changePassword(ChangeUserPasswordDto changeUserPasswordDto);
}
