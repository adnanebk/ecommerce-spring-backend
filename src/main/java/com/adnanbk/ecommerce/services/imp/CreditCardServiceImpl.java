package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.exceptions.CardNotFoundException;
import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.reposetories.CreditCardRepo;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepo creditCardRepo;
    private UserRepo userRepo;



    @Override
    @Transactional
    public CreditCard saveCard(CreditCard creditCard, String email) {
        AppUser user = userRepo.findByEmail(email);
        activeCreditCardIfNoneIsActive(creditCard, email);
        creditCard.setAppUser(user);
        return creditCardRepo.save(creditCard);
    }

    private void activeCreditCardIfNoneIsActive(CreditCard creditCard, String email) {
        if (!creditCardRepo.existsByAppUser_Email(email))
            creditCard.setActive(true);
    }

    @Override
    public void activateCreditCard(long id) {
        creditCardRepo.findById(id).orElseThrow(() -> new CardNotFoundException("no card founded with id " + id));
        var currentCard = creditCardRepo.findCurrentActivatedCard();
        creditCardRepo.updateActiveCard(id,true);

        currentCard.ifPresent(_card->creditCardRepo.updateActiveCard(_card.getId(),false));


    }
}
