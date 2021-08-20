package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.models.CreditCard;
import com.adnanbk.ecommerceang.reposetories.CreditCardRepo;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import com.adnanbk.ecommerceang.services.CreditCardService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepo creditCardRepo;
    private UserRepo  userRepo;

    public CreditCardServiceImpl(CreditCardRepo creditCardRepo, UserRepo userRepo) {
        this.creditCardRepo = creditCardRepo;
        this.userRepo = userRepo;
    }

    @Override
    public CreditCard saveCard(CreditCard creditCard, String userName) {

        AppUser user=userRepo.findByUserName(userName);
        creditCard.setAppUser(user);
       return creditCardRepo.save(creditCard);
    }

    @Override
    public Iterable<CreditCard> activatedCreditCard(CreditCard creditCard) {
      var cards=  creditCardRepo.findAll();
      cards.forEach(card->{
          if(card.getId().equals(creditCard.getId())) {
              card.setActive(creditCard.getActive());
              creditCardRepo.save(card);
          }
          else if(creditCard.getActive() && card.getActive())
                {
                    card.setActive(false);
                    creditCardRepo.save(card);
                }
      });
        return IterableUtils.toList(cards).stream().sorted(Comparator.comparing(CreditCard::getActive).reversed()).toList();
    }
}