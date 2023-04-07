package com.adnanbk.ecommerce.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface JwtTokenService {
    //generate value for user
    Tokens generateTokens(String subject);


    String validateTokenAndGetSubject(String token) throws JWTVerificationException;

    void setAuthenticationToken(String email, String password, Collection<String> roles, HttpServletRequest request);
}
