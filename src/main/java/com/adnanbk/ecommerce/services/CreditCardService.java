package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.CreditCard;

import java.util.List;

public interface CreditCardService {

    CreditCard saveCard(CreditCard creditCard, String name);

    void activateCreditCard(long id);

    List<CreditCard> getByEmail(String email);

    CreditCard update(CreditCard creditCard, Long id,String email);
}
