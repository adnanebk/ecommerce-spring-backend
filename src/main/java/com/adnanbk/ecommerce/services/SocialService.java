package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.JwtResponse;

public interface SocialService {
    boolean verify(JwtResponse jwtResponse);


}
