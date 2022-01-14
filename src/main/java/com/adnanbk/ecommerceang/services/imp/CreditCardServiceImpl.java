package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.models.CreditCard;
import com.adnanbk.ecommerceang.reposetories.CreditCardRepo;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import com.adnanbk.ecommerceang.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepo creditCardRepo;
    private UserRepo  userRepo;


    @Override
    public CreditCard saveCard(CreditCard creditCard, String userName) {
        AppUser user=userRepo.findByUserName(userName);
        if(!creditCardRepo.existsByAppUser_UserName(userName))
        creditCard.setActive(true);
        creditCard.setAppUser(user);
       return creditCardRepo.save(creditCard);
    }

    @Override
    public Iterable<CreditCard> activeCreditCard(CreditCard creditCard) {
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