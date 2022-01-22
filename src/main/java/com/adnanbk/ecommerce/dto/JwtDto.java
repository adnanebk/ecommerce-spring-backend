package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class JwtDto {
    @NotEmpty
    private String token;
    private String refreshToken;
    @NotNull
    private UserDto appUser;

    public JwtDto(String token, String refreshToken, UserDto appUser) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.appUser = appUser;
    }

}
