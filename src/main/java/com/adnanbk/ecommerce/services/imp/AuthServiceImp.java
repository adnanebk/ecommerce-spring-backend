package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.dto.AuthDataDto;
import com.adnanbk.ecommerce.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerce.dto.LoginUserDto;
import com.adnanbk.ecommerce.dto.UserOutputDto;
import com.adnanbk.ecommerce.exceptions.InvalidPasswordException;
import com.adnanbk.ecommerce.exceptions.InvalidTokenException;
import com.adnanbk.ecommerce.jwt.JwtTokenUtil;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.reposetories.RoleRepository;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.SocialService;
import com.adnanbk.ecommerce.utils.ConfirmationTokenUtil;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

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

    @Value("${front.url}")
    private String frontUrl;
    @Value("#{${jwt.expiration-time-minutes}*60*1000}")
    private long jwtExpirationTime;
    @Value("#{${jwtRefresh.expiration-time-days}*60*1000*1440}")
    private long jwtRefreshExpirationTime;

    @Override
    public AuthDataDto handleLoginWithGoogle(AuthDataDto authDataDto) {
        boolean isTokenValid = googleService.verify(authDataDto.getToken());
        if (!isTokenValid)
            throwInvalidCredentialException("error.invalid-credential");
        return doLoginSocialUser(authDataDto.getAppUser());
    }

    @Override
    public AuthDataDto handleLoginWithFacebook(AuthDataDto authDataDto) {
        boolean isTokenValid = facebookService.verify(authDataDto.getToken());
        if (!isTokenValid)
            throwInvalidCredentialException("error.invalid-credential");
        return doLoginSocialUser(authDataDto.getAppUser());
    }


    @Override
    public AuthDataDto handleLogin(LoginUserDto appUser) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throwInvalidCredentialException("error.invalid-email-or-password");
        }

        var currentUser = userRepo.findByEmail(appUser.getEmail()).orElseThrow();

        return generateTokens(currentUser);
    }

    @Override
    public AuthDataDto handleRegister(AppUser user) {
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user = saveUser(user);
        return generateTokens(user);
    }



    @Override
    public AuthDataDto refreshJwtToken(String refreshToken) {
        String email = this.jwtTokenUtil.validateTokenAndReturnSubject(refreshToken);
        return  this.userRepo.findByEmail(email).map(user -> generateTokens(user, refreshToken)).orElseThrow();
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

    private AuthDataDto generateTokens(AppUser user, String refreshToken) {
        var tokenExpirationDate = new Date(System.currentTimeMillis()+jwtExpirationTime);
        var refreshTokenExpirationDate = new Date(System.currentTimeMillis()+jwtRefreshExpirationTime);
        String token = this.jwtTokenUtil.generateToken(user.getEmail(), generateClaims(user),tokenExpirationDate);
        refreshToken = Objects.requireNonNullElse(refreshToken,
                this.jwtTokenUtil.generateToken(user.getEmail(), new HashMap<>(),refreshTokenExpirationDate));
        UserOutputDto userDto = new UserOutputDto();
        BeanUtils.copyProperties(user, userDto);
        return new AuthDataDto(token, refreshToken, userDto,tokenExpirationDate);
    }

    private AuthDataDto doLoginSocialUser(UserOutputDto user) {
        var appUser = userRepo.findByEmail(user.getEmail())
                .orElseGet(()->saveUser(new AppUser( user.getEmail(), user.getFirstName(), user.getLastName(),user.getImageUrl(), generateRandomPassword(),true,true)));
        return generateTokens(appUser);
    }

    private AuthDataDto generateTokens(AppUser user) {
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
        IntStream.range(0,6)
                .forEach(number->
                        sb.append(chars.charAt(random.nextInt(chars.length())))
                );

        return sb.toString();
    }

    private AppUser saveUser(AppUser user) {
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        return userRepo.save(user);
    }

    private void throwInvalidCredentialException(String code) {
        throw new BadCredentialsException(messagesUtil.getDefaultMessage(code));
    }

}
