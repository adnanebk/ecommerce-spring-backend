package com.adnanbk.ecommerceang.services;

import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.UserDto;

import java.security.SecureRandom;

public interface SocialService {
    UserDto verify(JwtResponse jwtResponse);


}
