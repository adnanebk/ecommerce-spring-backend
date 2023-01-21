package com.adnanbk.ecommerce.jwt;

import com.adnanbk.ecommerce.models.AppUser;
import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenService {
    //generate value for user
    JwtTokenServiceImp.Token generateAccessToken(String subject);

    JwtTokenServiceImp.Token generateRefreshToken(String subject);

    String validateTokenAndGetSubject(String token) throws JWTVerificationException;

    void setAuthenticationToken(AppUser user, HttpServletRequest request);
}
