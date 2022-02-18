package com.adnanbk.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


public record LoginUserDto(@NotEmpty String userName, @NotEmpty String password) {
}
