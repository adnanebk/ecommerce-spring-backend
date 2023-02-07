package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.dto.SocialLoginDto;
import com.adnanbk.ecommerce.exceptions.InvalidPasswordException;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.RoleRepository;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.SocialService;
import com.adnanbk.ecommerce.utils.ErrorMessagesUtil;
import com.adnanbk.ecommerce.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepo userRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncode;
    private  final ErrorMessagesUtil messagesUtil;
    private final AuthenticationManager authenticationManager;



    @Override
    public AppUser handleSocialLogin(SocialLoginDto user, SocialService socialService) {
        if (!socialService.verify(user.token()))
            throw new BadCredentialsException(messagesUtil.getDefaultMessage(("error.invalid-credential")));
        return getAppUserOrCreateNewOne(user);
    }

    @Override
    public AppUser handleLogin(LoginUserDto appUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException(messagesUtil.getDefaultMessage(("error.invalid-email-or-password")));
        }

        return userRepo.findByEmail(appUser.getEmail()).orElseThrow();
    }

    @Override
    public AppUser handleRegister(AppUser user) {
        user.setPassword(passwordEncode.encode(user.getPassword()));
        return saveUser(user);
    }


    @Override
    public AppUser getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
         return  userRepo.findByEmail(authentication.getName()).orElseThrow();
    }

    @Override
    public void changePassword(ChangeUserPasswordDto changeUserPasswordDto) {
        AppUser user = this.getAuthenticatedUser();
        if (!passwordEncode.matches(changeUserPasswordDto.getCurrentPassword(), user.getPassword()))
            throw new InvalidPasswordException(messagesUtil.getDefaultMessage("error.invalid-password"));
        var newPassword = passwordEncode.encode(changeUserPasswordDto.getNewPassword());
        userRepo.updatePassword(user.getId(),newPassword);
    }

    @Override
    public AppUser getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow();
    }


    private AppUser getAppUserOrCreateNewOne(SocialLoginDto user) {
        return userRepo.findByEmail(user.email())
                .orElseGet(() -> saveUser(
                        AppUser.builder().email(user.email()).firstName(user.firstName())
                                .lastName(user.lastName()).imageUrl(user.image())
                                .password(StringUtil.generateRandomPassword())
                                .enabled(true).isSocial(true).build()));
    }

    private AppUser saveUser(AppUser user) {
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        return userRepo.save(user);
    }

}
