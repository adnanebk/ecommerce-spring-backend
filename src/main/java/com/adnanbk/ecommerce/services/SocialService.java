package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.dto.JwtDto;

public interface SocialService {
    boolean verify(JwtDto jwtDto);


}
