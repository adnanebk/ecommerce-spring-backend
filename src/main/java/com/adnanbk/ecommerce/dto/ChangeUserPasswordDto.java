package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


public record ChangeUserPasswordDto(@NotEmpty String currentPassword, @NotEmpty String newPassword) {

}
