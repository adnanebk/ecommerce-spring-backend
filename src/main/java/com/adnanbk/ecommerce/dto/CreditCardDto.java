package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardDto {

    private Long id;
    private String cardType;
    private Boolean active;

    private String cardNumber;

    private String expirationDate;



}
