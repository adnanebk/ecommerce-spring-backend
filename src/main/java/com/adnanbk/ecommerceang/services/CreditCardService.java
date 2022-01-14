package com.adnanbk.ecommerceang.services;

import com.adnanbk.ecommerceang.models.CreditCard;

public interface CreditCardService {
    CreditCard saveCard(CreditCard creditCard, String name);

    Iterable<CreditCard> activeCreditCard(CreditCard creditCard);
}
