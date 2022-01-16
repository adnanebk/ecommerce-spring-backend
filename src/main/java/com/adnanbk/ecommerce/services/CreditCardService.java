package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.CreditCard;

public interface CreditCardService {
    CreditCard saveCard(CreditCard creditCard, String name);

    Iterable<CreditCard> activeCreditCard(CreditCard creditCard);
}
