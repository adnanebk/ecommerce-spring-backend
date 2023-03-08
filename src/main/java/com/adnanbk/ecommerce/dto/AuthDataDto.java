package com.adnanbk.ecommerce.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


public record AuthDataDto( @NotEmpty String token,String refreshToken,Date expirationDate,@NotNull UserOutputDto appUser) {
}
