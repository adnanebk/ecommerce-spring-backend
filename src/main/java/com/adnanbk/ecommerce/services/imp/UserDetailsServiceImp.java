package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.UserNotEnabledException;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Primary
@AllArgsConstructor
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserNotEnabledException {
        var user = userRepo.findByUserName(username);
        if (user == null)
            return null;
        return new User(user.getUserName(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE-USER")));
    }
}
