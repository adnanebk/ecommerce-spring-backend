package com.adnanbk.ecommerceang.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangeUserPasswordDto {

    @NotEmpty
    private String currentPassword;
    @NotEmpty
    private String newPassword;
}
