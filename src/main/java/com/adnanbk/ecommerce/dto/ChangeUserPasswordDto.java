package com.adnanbk.ecommerce.dto;

import javax.validation.constraints.NotEmpty;


public record ChangeUserPasswordDto(@NotEmpty String currentPassword, @NotEmpty String newPassword) {

}
