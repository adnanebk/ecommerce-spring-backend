package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.CreditCard;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {

    CreditCard saveCard(CreditCard creditCard);

    void activateCreditCard(Long id);

    List<CreditCard> getAuthenticatedUserCreditCards();

    void update(CreditCard creditCard, Long id);


    Optional<CreditCard> getByCardNumber(String cardNumber);

    void removeById(Long id);
}
