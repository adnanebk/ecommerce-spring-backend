package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.Jwt.JwtTokenUtil;
import com.adnanbk.ecommerceang.dto.ChangeUserPasswordDto;
import com.adnanbk.ecommerceang.dto.JwtResponse;
import com.adnanbk.ecommerceang.dto.LoginUserDto;
import com.adnanbk.ecommerceang.dto.RegisterUserDto;
import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import com.adnanbk.ecommerceang.services.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthServiceImp implements AuthService {

    private  UserRepo userRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private PasswordEncoder passwordEncode;
    private AuthenticationManager authenticationManager;
    private EmailSenderService emailSenderService;


    public AuthServiceImp(UserRepo userRepo, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncode, AuthenticationManager authenticationManager, EmailSenderService emailSenderService) throws BadCredentialsException {
        this.userRepo = userRepo;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncode = passwordEncode;
        this.authenticationManager = authenticationManager;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public JwtResponse handleLogin(LoginUserDto appUser){
      var currentUser  = userRepo.findByUserName(appUser.getUserName());
       if (currentUser==null)
            throw new BadCredentialsException("Invalid username or password");

        //UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(appUser.getUserName(), appUser.getPassword());

/*        try {
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }*/
        if(!passwordEncode.matches(appUser.getPassword(),currentUser.getPassword()))
            throw new BadCredentialsException("Invalid username or password");


        final String token = jwtTokenUtil.generateToken(appUser.getUserName(),generateClaims(currentUser));
        RegisterUserDto registerUserDto = new RegisterUserDto();
        BeanUtils.copyProperties(currentUser,registerUserDto);
        return new JwtResponse(token,registerUserDto);
    }
    @Override
    public JwtResponse handleRegister(AppUser user)
    {
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user= userRepo.save(user);
        String token = this.jwtTokenUtil.generateToken(user.getUserName(),generateClaims(user));
        RegisterUserDto registerUserDto = new RegisterUserDto();
        BeanUtils.copyProperties(user,registerUserDto);
           if(!user.isEnabled())
            emailSenderService.sendEmailConfirmation(user);

        return   new JwtResponse(token,registerUserDto);
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


    private HashMap<String,Object> generateClaims(AppUser appUser){
       var claims =new HashMap<String,Object>();
       claims.put("email",appUser.getEmail());

       return claims;
   };
}
