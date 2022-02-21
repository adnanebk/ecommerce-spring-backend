package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.JwtDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.dto.UserDto;
import com.adnanbk.ecommerce.jwt.JwtTokenUtil;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.SocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepo userRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncode;
    private final SocialService googleService;
    private final SocialService facebookService;


    @Value("${jwt.expiration-time}")
    private long expirationTime;

    @Override
    public JwtDto handleLoginWithGoogle(JwtDto jwtDto) {
        googleService.verify(jwtDto);
        return doLoginSocialUser(jwtDto.getAppUser());
    }

    @Override
    public JwtDto handleLoginWithFacebook(JwtDto jwtDto) {
        boolean isTokenValid = facebookService.verify(jwtDto);
        if (!isTokenValid)
            throw new BadCredentialsException("Invalid credentials");
        return doLoginSocialUser(jwtDto.getAppUser());
    }


    @Override
    public JwtDto handleLogin(LoginUserDto appUser) {
        var currentUser = userRepo.findByUserName(appUser.getUserName());
      /*  try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUserName(), appUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
        */

        if (currentUser == null || !passwordEncode.matches(appUser.getPassword(), currentUser.getPassword()))
            throw new BadCredentialsException("Invalid username or password");

        return generateTokens(currentUser);
    }

    @Override
    public JwtDto handleRegister(AppUser user) {
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user = userRepo.save(user);


        return generateTokens(user);
    }


    @Override
    public void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String userName) {
        var user = userRepo.findByUserName(userName);
        if (user == null || !passwordEncode.matches(changeUserPasswordDto.getCurrentPassword(), user.getPassword()))
            throw new BadCredentialsException("current password not exists or invalid");
        user.setPassword(passwordEncode.encode(changeUserPasswordDto.getNewPassword()));
        userRepo.save(user);
    }

    @Override
    public JwtDto refreshNewToken(String refreshToken) {
        String userName = this.jwtTokenUtil.validateTokenAndReturnSubject(refreshToken);
        var user = this.userRepo.findByUserName(userName);
        return user != null ? generateTokens(user, refreshToken) : null;
    }

    private JwtDto generateTokens(AppUser user, String refreshToken) {
        String token = this.jwtTokenUtil.generateToken(user.getUserName(), generateClaims(user));
        refreshToken = Objects.requireNonNullElse(refreshToken,
                this.jwtTokenUtil.generateRefreshToken(user.getUserName(), new HashMap<>()));
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setExpirationDate(new Date(System.currentTimeMillis() + (expirationTime * 60 * 1000)));
        return new JwtDto(token, refreshToken, userDto);
    }

    private JwtDto doLoginSocialUser(UserDto user) {
        AppUser appUser = userRepo.findByUserName(user.getUserName());
        if (appUser == null) {
            appUser = new AppUser(user.getUserName(), user.getEmail(), user.getFirstName(), user.getLastName(), generateRandomPassword());
            appUser.setEnabled(true);
            appUser.setSocial(true);
            appUser = userRepo.save(appUser);
        }

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

}
