package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class JwtDto {
    @NotEmpty
    private String token;
    private String refreshToken;

    private Date expirationDate;


    @NotNull
    private UserDto appUser;

    public JwtDto(String token, String refreshToken, UserDto appUser,Date expirationDate) {
        this.token = token;
        this.expirationDate=expirationDate;
        this.refreshToken = refreshToken;
        this.appUser = appUser;
    }

}
