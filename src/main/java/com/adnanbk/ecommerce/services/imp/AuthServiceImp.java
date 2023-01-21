package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.*;
import com.adnanbk.ecommerce.exceptions.InvalidPasswordException;
import com.adnanbk.ecommerce.exceptions.InvalidTokenException;
import com.adnanbk.ecommerce.jwt.JwtTokenService;
import com.adnanbk.ecommerce.jwt.JwtTokenServiceImp;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.RoleRepository;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.SocialService;
import com.adnanbk.ecommerce.utils.ConfirmationTokenUtil;
import com.adnanbk.ecommerce.utils.ErrorMessagesUtil;
import com.adnanbk.ecommerce.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepo userRepo;
    private final RoleRepository roleRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncode;
    private  final ErrorMessagesUtil messagesUtil;
    private final AuthenticationManager authenticationManager;

    @Value("${front.url}")
    private String frontUrl;



    @Override
    public AuthDataDto handleSocialLogin(SocialLoginDto user, SocialService socialService) {
        if (!socialService.verify(user.token()))
            throw new BadCredentialsException(messagesUtil.getDefaultMessage(("error.invalid-credential")));
        return buildAuthData(getAppUserOrCreateNewOne(user));
    }

    @Override
    public AuthDataDto handleLogin(LoginUserDto appUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException(messagesUtil.getDefaultMessage(("error.invalid-email-or-password")));
        }

        return buildAuthData(userRepo.findByEmail(appUser.getEmail()).orElseThrow());
    }

    @Override
    public AuthDataDto handleRegister(AppUser user) {
        user.setPassword(passwordEncode.encode(user.getPassword()));
        return buildAuthData(saveUser(user));
    }

    @Override
    public AuthDataDto refreshJwtToken(String refreshToken) {
        String email = this.jwtTokenService.validateTokenAndGetSubject(refreshToken);
        return  this.userRepo.findByEmail(email).map(this::buildAuthData).orElseThrow();

    }


    @Override
    public String enableUser(String token) {
        var user = verifyConfirmationTokenAndGetUser(token);
        userRepo.enableUser(user.getId(),true);
        return frontUrl + "?verified=true";
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

    private AppUser verifyConfirmationTokenAndGetUser(String token) {
          return Optional.ofNullable(ConfirmationTokenUtil.getConfirmationToken(token))
                    .map(confirmationToken->{
                        if (confirmationToken.getExpirationDate().isBefore(LocalDate.now()))
                           throw new InvalidTokenException("token is expired");
                        if(confirmationToken.getAppUser().isEnabled())
                            throw new InvalidTokenException("token is already enabled");
                        return confirmationToken.getAppUser();
                        }
                    ).orElseThrow(()->new InvalidTokenException("the token is not found"));
    }

    private AuthDataDto buildAuthData(AppUser user) {
        UserOutputDto userDto = new UserOutputDto();
        BeanUtils.copyProperties(user, userDto);
        JwtTokenServiceImp.Token token = this.jwtTokenService.generateAccessToken(user.getEmail());
        JwtTokenServiceImp.Token refreshToken = this.jwtTokenService.generateRefreshToken(user.getEmail());
        return new AuthDataDto(token.value(),refreshToken.value(),token.expirationDate(),userDto);

    }

    private AppUser getAppUserOrCreateNewOne(SocialLoginDto user) {
        return userRepo.findByEmail(user.email())
                .orElseGet(() -> saveUser(
                        AppUser.builder().email(user.email()).firstName(user.firstName())
                                .lastName(user.lastName()).imageUrl(user.image())
                                .password(PasswordUtil.generateRandomPassword())
                                .enabled(true).isSocial(true).build()));
    }

    private AppUser saveUser(AppUser user) {
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        return userRepo.save(user);
    }

}
