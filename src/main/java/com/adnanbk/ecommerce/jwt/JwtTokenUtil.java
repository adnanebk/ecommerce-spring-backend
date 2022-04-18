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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {



    @Value("${jwt.secret}")
    private String secret;
    private Algorithm algorithm;


    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC512(secret.getBytes());
    }

    //generate token for user
    public String generateToken(String email, Map<String, Object> claims,Date expirationTime) {
        return doGenerateToken(email, expirationTime, claims);
    }


    private String doGenerateToken(String email, Date expirationDate, Map<String, Object> claims) {
        return JWT.create().withSubject(email)
                .withExpiresAt(expirationDate)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("claims", claims).sign(algorithm);
    }


    public String validateTokenAndReturnSubject(String token) throws JWTVerificationException {
        var verifier = JWT.require(algorithm).build();
        var decodedJwt = verifier.verify(token);
        return decodedJwt.getSubject();

    }

    public void setAuthenticationToken(AppUser user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword(), user.getRoles().stream().map(us->new SimpleGrantedAuthority(us.getName())).toList());
        // Stores additional details about the authentication request (IP address, certificate serial number etc.).
        if (request != null)
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    }
}
