package com.adnanbk.ecommerce.jwt;

import com.adnanbk.ecommerce.exceptions.UserNotEnabledException;
import com.adnanbk.ecommerce.models.Role;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${api.publicPaths}")
    private Set<String> publicPaths;
    private  HandlerExceptionResolver handlerExceptionResolver;

    private JwtTokenService jwtTokenService;
    private UserRepo userRepo;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String header =request.getHeader("Authorization");
        String path =request.getRequestURI();
        String method =request.getMethod();
        return (!StringUtils.hasLength(header) || !header.startsWith("Bearer "))
                || method.equalsIgnoreCase("get") && (path.contains("products") || path.contains("categories"))
                || publicPaths.stream().anyMatch(p->path.startsWith(p.replace("**","")));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            var token = header.split("Bearer ")[1];
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // Once we get the token we validate it.
                String email = jwtTokenService.validateTokenAndGetSubject(token);
                userRepo.findByEmail(email).ifPresent(user->{
                    if(!user.isEnabled())
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
}
