package com.adnanbk.ecommerce.jwt;

import com.adnanbk.ecommerce.exceptions.UserNotEnabledException;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.models.Role;
import com.adnanbk.ecommerce.reposetories.UserRepo;
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
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private  HandlerExceptionResolver handlerExceptionResolver;

    private JwtTokenService jwtTokenService;
    private UserRepo userRepo;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path =request.getRequestURI();
        return path.contains("refresh-token");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            var token = Optional.ofNullable(header)
                                       .map(h->h.split("Bearer "))
                                       .filter(arr->arr.length==2)
                                       .map(arr->arr[1]);
            if (token.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Once we get the token we validate it.
                String email = jwtTokenService.validateTokenAndGetSubject(token.get());
                userRepo.findByEmail(email).ifPresent(user->{
                    if(!isUserEnabledOrAllowed(request, user))
                        throw new UserNotEnabledException();
                    // authentication
                    jwtTokenService.setAuthenticationToken(email,user.getPassword(),user.getRoles().stream().map(Role::getName).toList(),request);
                });

            }
        } catch (RuntimeException ex){

           handlerExceptionResolver.resolveException(request,response,null,ex);
            return; // return from this method so the response not enter to the chain and duplicate the exceptions
        }
        chain.doFilter(request, response);

    }

    private static boolean isUserEnabledOrAllowed(HttpServletRequest request, AppUser user) {
        return user.isEnabled() || request.getMethod().equalsIgnoreCase("get")
                || request.getRequestURI().contains("send-confirmation")
                || request.getRequestURI().contains("users/");
    }
}
