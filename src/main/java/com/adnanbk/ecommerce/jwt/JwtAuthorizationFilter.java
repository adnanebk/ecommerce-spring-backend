package com.adnanbk.ecommerce.jwt;

import com.adnanbk.ecommerce.exceptions.UserNotEnabledException;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private  HandlerExceptionResolver handlerExceptionResolver;

    private JwtTokenUtil jwtTokenUtil;
    private UserRepo userRepo;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String reqUri = request.getRequestURI();
        return !reqUri.contains("orders") &&
                !reqUri.contains("user") &&
                !reqUri.contains("/api/auth/user/**") &&
                !reqUri.contains("products/v2") &&
                !reqUri.contains("creditCards");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = Objects.requireNonNullElse
                (request.getHeader("Authorization"), "");
        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            var tokenArr = requestTokenHeader.split("Bearer ");
            if (tokenArr.length != 2)
                throw new JWTVerificationException("Request header not contains a valid authorization");

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // Once we get the token we validate it.
                String email = jwtTokenUtil.validateTokenAndReturnSubject(tokenArr[1]);
                userRepo.findByEmail(email).ifPresent(user->{
                    if(!user.isEnabled())
                        throw new UserNotEnabledException();
                    // authentication
                    jwtTokenUtil.setAuthenticationToken(user, request);
                });

            }
        } catch (RuntimeException ex){
           handlerExceptionResolver.resolveException(request,response,null,ex);
            return; // return from this method so the response not enter to the chain and duplicate the exceptions
        }
        chain.doFilter(request, response);

    }
}
