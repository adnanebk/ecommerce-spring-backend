package com.adnanbk.ecommerceang.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
}
