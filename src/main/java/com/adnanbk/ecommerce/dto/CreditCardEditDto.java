package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.validations.cardExpirationDate.FutureExpirationDate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CreditCardEditDto {

    @NotEmpty(message = "{error.empty}")
    @Pattern(regexp = "VISA|MASTERCARD")
    private String cardType;


    @NotNull(message = "{error.empty}")
    @Pattern(regexp = "^(?:(?<visa>[0-9]{12}(?:[0-9]{3})?)|(?<mastercard>[0-9]{16}))$"
            , message = "{error.regExp}")
    private String cardNumber;

    @NotEmpty(message = "{error.empty}")
    @FutureExpirationDate
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$"
            , message = "{error.regExp}")
    private String expirationDate;

}
