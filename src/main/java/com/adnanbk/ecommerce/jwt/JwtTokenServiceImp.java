package com.adnanbk.ecommerce.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;

@Service
@ConfigurationProperties(prefix = "jwt")
@Setter
public class JwtTokenServiceImp implements JwtTokenService {


    private String secret;
    private Algorithm algorithm;
    private long tokenExpirationTime;
    private long refreshTokenExpirationTime;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC512(secret.getBytes());
        tokenExpirationTime *= 60 * 1000;
        refreshTokenExpirationTime *= 60 * 1000 * 1440;
    }


    @Override
    public Tokens generateTokens(String subject) {
        var expirationDate = new Date(System.currentTimeMillis() + tokenExpirationTime);
        var refreshExpirationDate = new Date(System.currentTimeMillis() + refreshTokenExpirationTime);
        String accessToken = generateToken(subject, expirationDate);
        String refreshToken = generateToken(subject, refreshExpirationDate);
        return new Tokens(accessToken, refreshToken, expirationDate, refreshExpirationDate);
    }


    @Override
    public String validateTokenAndGetSubject(String token) throws JWTVerificationException {
        var verifier = JWT.require(algorithm).build();
        var decodedJwt = verifier.verify(token);
        return decodedJwt.getSubject();
    }

    @Override
    public void setAuthenticationToken(String email, String password, Collection<String> roles, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                email, password, roles.stream().map(SimpleGrantedAuthority::new).toList());
        // Stores additional details about the authentication request (IP address, certificate serial number etc.).
        if (request != null)
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    }

    private String generateToken(String subject, Date expirationDate) {
        return JWT.create().withSubject(subject).withExpiresAt(expirationDate)
                .withIssuedAt(new Date(System.currentTimeMillis())).sign(algorithm);
    }

}


