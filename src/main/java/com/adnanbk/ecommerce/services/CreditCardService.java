package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.CreditCard;

import java.util.List;

public interface CreditCardService {
    List<CreditCard> findByUserName(String userName);

    CreditCard saveCard(CreditCard creditCard, String name);

    Iterable<CreditCard> activateCreditCard(CreditCard creditCard);
}
