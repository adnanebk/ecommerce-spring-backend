package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ChangeUserPasswordDto {

    @NotEmpty
    private String currentPassword;
    @NotEmpty
    private String newPassword;
}
