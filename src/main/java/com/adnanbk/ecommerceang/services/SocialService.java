package com.adnanbk.ecommerceang.services;

import com.adnanbk.ecommerceang.dto.JwtResponse;

public interface SocialService {
    boolean verify(JwtResponse jwtResponse);


}
