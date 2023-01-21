package com.adnanbk.ecommerce.jwt;

import com.adnanbk.ecommerce.models.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtTokenServiceImp implements JwtTokenService {

    @Value("${jwt.secret}")
    private String secret;
    private Algorithm algorithm;

    @Value("#{${jwt.expiration-time-minutes}*60*1000}")
    private long jwtExpirationTime;

    @Value("#{${jwtRefresh.expiration-time-days}*60*1000*1440}")
    private long jwtRefreshExpirationTime;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC512(secret.getBytes());
    }

    //generate value for user
    @Override
    public Token generateAccessToken(String subject) {
        var expirationDate = new Date(System.currentTimeMillis()+jwtExpirationTime);
        String value = JWT.create().withSubject(subject).withExpiresAt(expirationDate)
                .withIssuedAt(new Date(System.currentTimeMillis())).sign(algorithm);
        return new Token(value,expirationDate);
    }
    @Override
    public Token generateRefreshToken(String subject) {
        var expirationDate = new Date(System.currentTimeMillis()+jwtRefreshExpirationTime);
        String token = JWT.create().withSubject(subject).withExpiresAt(expirationDate)
                .withIssuedAt(new Date(System.currentTimeMillis())).sign(algorithm);
        return new Token(token,expirationDate);
    }



    @Override
    public String validateTokenAndGetSubject(String token) throws JWTVerificationException {
        var verifier = JWT.require(algorithm).build();
        var decodedJwt = verifier.verify(token);
        return decodedJwt.getSubject();

    }

    @Override
    public void setAuthenticationToken(AppUser user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword(), user.getRoles().stream().map(us->new SimpleGrantedAuthority(us.getName())).toList());
        // Stores additional details about the authentication request (IP address, certificate serial number etc.).
        if (request != null)
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    }

     public record Token(String value, Date expirationDate){}

}


