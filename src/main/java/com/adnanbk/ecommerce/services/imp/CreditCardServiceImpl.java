package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.reposetories.CreditCardRepo;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

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
    public Iterable<CreditCard> activeCreditCard(CreditCard creditCard) {
        var cards = creditCardRepo.findAll();
        cards.forEach(card -> {
            if (card.getId().equals(creditCard.getId())) {
                card.setActive(creditCard.getActive());
                creditCardRepo.save(card);
            } else if (creditCard.getActive() && card.getActive()) {
                card.setActive(false);
                creditCardRepo.save(card);
            }
        });
        return IterableUtils.toList(cards).stream().sorted(Comparator.comparing(CreditCard::getActive).reversed()).toList();
    }
}