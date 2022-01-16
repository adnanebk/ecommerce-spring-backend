package com.adnanbk.ecommerceang.Jwt;

import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {


    private JwtTokenUtil jwtTokenUtil;
    private UserRepo userRepo;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String reqUri = request.getRequestURI();
        return !reqUri.contains("userOrders") &&
                !reqUri.contains("appUsers") &&
                !reqUri.contains("creditCards");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = Objects.requireNonNullElse
                (request.getHeader("Authorization"), "");
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        var tokenArr = requestTokenHeader.split("Bearer ");
        if (tokenArr.length != 2)
            throw new JWTVerificationException("Header not contains Authorization or JWT Token does not begin with Bearer");

	/*		response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpStatus.BAD_REQUEST.value());*/

        // Once we get the token validate it.
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String userName = jwtTokenUtil.validateTokenAndReturnSubject(tokenArr[1]);
                AppUser appUser = userRepo.findByUserName(userName);

                boolean isUserEnabled = appUser != null && appUser.isEnabled();
                // authentication
                if (isUserEnabled) {
                    jwtTokenUtil.setAuthenticationToken(appUser.getUserName(), appUser.getPassword(), request);
                }
            } catch (JWTVerificationException ex) {
                response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
            }

        }
        chain.doFilter(request, response);

    }
}