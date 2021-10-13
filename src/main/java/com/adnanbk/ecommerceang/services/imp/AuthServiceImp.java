package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.Jwt.JwtTokenUtil;
import com.adnanbk.ecommerceang.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.LoginUserDto;
import com.adnanbk.ecommerceang.dto.UserDto;
import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import com.adnanbk.ecommerceang.services.AuthService;
import com.sun.mail.imap.protocol.IMAPSaslAuthenticator;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final   UserRepo userRepo;
    private final   JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncode;
    private final EmailSenderService emailSenderService;
    private final AuthenticationManager authenticationManager;


    @Value("${jwt.expiration-time}")
    private long expirationTime;


    @Override
    public JwtResponse handleLogin(LoginUserDto appUser){
      var currentUser  = userRepo.findByUserName(appUser.getUserName());

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUserName(), appUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException | NullPointerException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
/*        if(!passwordEncode.matches(appUser.getPassword(),currentUser.getPassword()))
            throw new BadCredentialsException("Invalid username or password");*/

        return generateTokens(currentUser);
    }
    @Override
    public JwtResponse handleRegister(AppUser user)
    {
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user= userRepo.save(user);

           if(!user.isEnabled())
            emailSenderService.sendEmailConfirmation(user);

        return  generateTokens(user,null);
    }

    @Override
    public boolean verify(String token) {
        AppUser user = emailSenderService.verifyToken(token);
        if(user!=null)
        {
            user.setEnabled(true);
            userRepo.save(user);
            return true;
        }
        return false;
    }
    @Override
    public void sendEmailConfirmation(String email){
        AppUser user=userRepo.findByEmail(email).orElseThrow(()-> new BadCredentialsException("could not found this email"));
        emailSenderService.sendEmailConfirmation(user);
    }

    @Override
    public void changePassword(ChangeUserPasswordDto changeUserPasswordDto, String userName) {
        var user = userRepo.findByUserName(userName);
        if(user==null || !passwordEncode.matches(changeUserPasswordDto.getCurrentPassword(),user.getPassword()))
            throw new BadCredentialsException("current password not exists or invalid");
      user.setPassword(passwordEncode.encode(changeUserPasswordDto.getNewPassword()));
      userRepo.save(user);
    }

    @Override
    public JwtResponse refreshNewToken(String refreshToken) {
        String userName = this.jwtTokenUtil.validateTokenAndReturnSubject(refreshToken);
        var user=this.userRepo.findByUserName(userName);
        return user!=null?generateTokens(user,refreshToken):null;
    }

    private JwtResponse generateTokens(AppUser user,String refreshToken){
        String token = this.jwtTokenUtil.generateToken(user.getUserName(),generateClaims(user));
        refreshToken = Objects.requireNonNullElse(refreshToken,
                       this.jwtTokenUtil.generateRefreshToken(user.getUserName(),new HashMap<>()));
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setExpirationDate(new Date(System.currentTimeMillis()+ (expirationTime*60*1000)));
        return   new JwtResponse(token,refreshToken, userDto);
    }
    @Override
    public JwtResponse generateTokens(AppUser user){
    return generateTokens(user,null);
    }
    private HashMap<String,Object> generateClaims(AppUser appUser){
       var claims =new HashMap<String,Object>();
       claims.put("email",appUser.getEmail());
       return claims;
   }

}
