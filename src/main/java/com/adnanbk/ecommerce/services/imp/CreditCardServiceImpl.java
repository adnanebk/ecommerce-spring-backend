package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.reposetories.CreditCardRepo;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepo creditCardRepo;
    private UserRepo userRepo;



    @Override
    @Transactional
    public CreditCard saveCard(CreditCard creditCard, String email) {
      return   userRepo.findByEmail(email).map(user->{
            activeCreditCardIfNoneIsActive(creditCard, email);
            creditCard.setAppUser(user);
            return creditCardRepo.save(creditCard);
        }).orElseThrow();

    }

    private void activeCreditCardIfNoneIsActive(CreditCard creditCard, String email) {
        if (!creditCardRepo.existsByAppUser_Email(email))
            creditCard.setActive(true);
    }

    @Override
    @Transactional
    public void activateCreditCard(long id) {
        var currentCard = creditCardRepo.findCurrentActivatedCard();
        creditCardRepo.updateActiveCard(id,true);

        currentCard.ifPresent(card->creditCardRepo.updateActiveCard(card.getId(),false));


    }

    @Override
    public List<CreditCard> getByEmail(String email) {
        return creditCardRepo.findAllByAppUser_Email(email);
    }

    @Override
    @Transactional
    public CreditCard update(CreditCard creditCard, Long id,String email) {

        return userRepo.findByEmail(email).map(user-> creditCardRepo.findById(id).map(card->{
               BeanUtils.copyProperties(creditCard,card);
               card.setAppUser(user);
               return this.creditCardRepo.save(card);
           }).orElseThrow()).orElseThrow();
    }
}
