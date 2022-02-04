package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.CardNotFoundException;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.reposetories.CreditCardRepo;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepo creditCardRepo;
    private UserRepo userRepo;


    @Override
    public CreditCard saveCard(CreditCard creditCard, String userName) {
        AppUser user = userRepo.findByUserName(userName);
        activeCrediCardIfNew(creditCard, userName);
        creditCard.setAppUser(user);
        return creditCardRepo.save(creditCard);
    }

    private void activeCrediCardIfNew(CreditCard creditCard, String userName) {
        if (!creditCardRepo.existsByAppUser_UserName(userName))
            creditCard.setActive(true);
    }

    @Override
    public Iterable<CreditCard> activateCreditCard(CreditCard creditCard) {
        var card = creditCardRepo.findById(creditCard.getId()).orElseThrow(()->new CardNotFoundException("no card founded with id "+creditCard.getId()));
        var currentCard = creditCardRepo.findCurrentActivatedCard().orElseThrow(()->new CardNotFoundException("card is not activated yet"));
          card.setActive(true);
          currentCard.setActive(true);
          creditCardRepo.save(card);
          creditCardRepo.save(currentCard);
        return creditCardRepo.findAllOrderByActiveDesc() ;
    }
}