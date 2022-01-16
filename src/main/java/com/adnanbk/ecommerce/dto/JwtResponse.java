package com.adnanbk.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class JwtResponse {
    @NotEmpty
    private String token;
    private String refreshToken;
    @NotNull
    private UserDto appUser;

    public JwtResponse(String token, String refreshToken, UserDto appUser) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.appUser = appUser;
    }

}
