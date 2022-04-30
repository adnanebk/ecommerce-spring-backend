package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.*;
import com.adnanbk.ecommerce.exceptions.InvalidPasswordException;
import com.adnanbk.ecommerce.jwt.JwtTokenUtil;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.RoleRepository;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.SocialService;
import com.adnanbk.ecommerce.utils.ErrorMessagesUtil;
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

import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepo userRepo;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncode;
    private final SocialService googleService;
    private final SocialService facebookService;
    private  final ErrorMessagesUtil messagesUtil;
    private final AuthenticationManager authenticationManager;

    @Value("#{${jwt.expiration-time-minutes}*60*1000}")
    private long jwtExpirationTime;
    @Value("#{${jwtRefresh.expiration-time-days}*60*1000*1440}")
    private long jwtRefreshExpirationTime;

    @Override
    public JwtDto handleLoginWithGoogle(JwtDto jwtDto) {
        googleService.verify(jwtDto);
        return doLoginSocialUser(jwtDto.getAppUser());
    }

    @Override
    public JwtDto handleLoginWithFacebook(JwtDto jwtDto) {
        boolean isTokenValid = facebookService.verify(jwtDto);
        if (!isTokenValid)
            throw new BadCredentialsException(messagesUtil.getDefaultMessage("error.invalid-credential"));
        return doLoginSocialUser(jwtDto.getAppUser());
    }


    @Override
    public JwtDto handleLogin(LoginUserDto appUser) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException(messagesUtil.getDefaultMessage("error.invalid-email-or-password"));
        }

        var currentUser = userRepo.findByEmail(appUser.getEmail()).orElseThrow();

        return generateTokens(currentUser);
    }

    @Override
    public JwtDto handleRegister(AppUser user) {
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user = saveUser(user);

        return generateTokens(user);
    }




    @Override
    public void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String email) {
      userRepo.findByEmail(email).ifPresent(user->{
          if (!passwordEncode.matches(changeUserPasswordDto.getCurrentPassword(), user.getPassword()))
              throw new InvalidPasswordException(messagesUtil.getDefaultMessage("error.invalid-password"),"currentPassword");
          var newPassword = passwordEncode.encode(changeUserPasswordDto.getNewPassword());
          userRepo.updatePassword(user.getId(),newPassword);
      });

    }

    @Override
    public JwtDto refreshNewToken(String refreshToken) {
        String email = this.jwtTokenUtil.validateTokenAndReturnSubject(refreshToken);
        return  this.userRepo.findByEmail(email).map(user -> generateTokens(user, refreshToken)).orElseThrow();

    }

    @Override
    public ImageDto changeUserImage(String fileName, String email) {
        userRepo.updateImage(email,fileName);
        return userRepo.findByEmail(email).map(user ->new ImageDto(user.getImageUrl())).orElseThrow();
    }

    @Override
    public Optional<AppUser> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    private JwtDto generateTokens(AppUser user, String refreshToken) {
        var tokenExpirationDate = new Date(System.currentTimeMillis()+jwtExpirationTime);
        var refreshTokenExpirationDate = new Date(System.currentTimeMillis()+jwtRefreshExpirationTime);
        String token = this.jwtTokenUtil.generateToken(user.getEmail(), generateClaims(user),tokenExpirationDate);
        refreshToken = Objects.requireNonNullElse(refreshToken,
                this.jwtTokenUtil.generateToken(user.getEmail(), new HashMap<>(),refreshTokenExpirationDate));
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return new JwtDto(token, refreshToken, userDto,tokenExpirationDate);
    }

    private JwtDto doLoginSocialUser(UserDto user) {
        var appUser = userRepo.findByEmail(user.getEmail())
                .orElse(saveUser(new AppUser( user.getEmail(), user.getFirstName(), user.getLastName(),user.getImageUrl(), generateRandomPassword(),true,true)));
        return generateTokens(appUser);
    }

    private JwtDto generateTokens(AppUser user) {
        return generateTokens(user, null);
    }

    private HashMap<String, Object> generateClaims(AppUser appUser) {
        var claims = new HashMap<String, Object>();
        claims.put("email", appUser.getEmail());
        return claims;
    }

    // Method to generate a random alphanumeric password of a specific length
    private String generateRandomPassword() {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    private AppUser saveUser(AppUser user) {
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        user = userRepo.save(user);
        return user;
    }

}
