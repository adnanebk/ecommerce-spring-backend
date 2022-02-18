package com.adnanbk.ecommerce.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public record JwtDto (
     @NotEmpty
     String token,
     String refreshToken,
     @NotNull
     UserDto appUser



){
}
