package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.UserNotEnabledException;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, UserNotEnabledException {
        return userRepo.findByEmail(email).map(user -> new User(user.getEmail(), user.getPassword(),
                        user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()))
                .orElseThrow(() -> new BadCredentialsException("error.invalid-email-or-password"));
    }
}
