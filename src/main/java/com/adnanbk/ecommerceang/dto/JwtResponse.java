package com.adnanbk.ecommerceang.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class JwtResponse {
    @NotEmpty
    private String token;
    @NotNull
    private RegisterUserDto appUser;

    public JwtResponse(String token,RegisterUserDto appUser) {
        this.token = token;
        this.appUser=appUser;
    }

}
