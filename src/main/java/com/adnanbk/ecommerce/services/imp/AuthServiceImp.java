package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.*;
import com.adnanbk.ecommerce.exceptions.InvalidPasswordException;
import com.adnanbk.ecommerce.jwt.JwtTokenService;
import com.adnanbk.ecommerce.mappers.UserMapper;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.RoleRepository;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.SocialService;
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
    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;
    private final UserMapper userMapper;

    @Override
    public AuthDataDto handleSocialLogin(SocialLoginDto user, SocialService socialService) {
        if (!socialService.verify(user.token()))
            throw new BadCredentialsException(("error.invalid-credential"));
        return buildAuthData(getAppUserOrCreateNewOne(user));
    }

    @Override
    public AuthDataDto handleLogin(LoginUserDto appUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.email(), appUser.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("error.invalid-email-or-password");
        }

        return userRepo.findByEmail(appUser.email()).map(this::buildAuthData).orElseThrow();
    }

    @Override
    public AuthDataDto handleRegister(UserInputDto user) {
        user.setPassword(passwordEncode.encode(user.getPassword()));
        return this.buildAuthData(saveUser(userMapper.toEntity(user)));
    }


    @Override
    public AppUser getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByEmail(authentication.getName()).orElseThrow();
    }

    @Override
    public void changePassword(ChangeUserPasswordDto changeUserPasswordDto) {
        var user = this.getAuthenticatedUser();
            if (!passwordEncode.matches(changeUserPasswordDto.getCurrentPassword(), user.getPassword()))
                throw new InvalidPasswordException("error.invalid-password");
            var newPassword = passwordEncode.encode(changeUserPasswordDto.getNewPassword());
            userRepo.updatePassword(user.getId(),newPassword);

    }



    private AppUser getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow();
    }

    @Override
    public AuthDataDto refreshNewToken(String refreshToken) {
        String email  = this.jwtTokenService.validateTokenAndGetSubject(refreshToken);
        return   buildAuthData(getUserByEmail(email));
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
    private AuthDataDto buildAuthData(AppUser user) {
       var tokens = this.jwtTokenService.generateTokens(user.getEmail());
       return new AuthDataDto(tokens.access(), tokens.refresh(), tokens.expirationDate(), userMapper.toDto(user));

    }

}
