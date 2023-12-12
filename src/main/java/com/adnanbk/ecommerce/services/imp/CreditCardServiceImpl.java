package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.reposetories.CreditCardRepo;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepo creditCardRepo;
    private final AuthService authService;


    @Override
    public CreditCard saveCard(CreditCard creditCard) {
       var user =   authService.getAuthenticatedUser();
            if(isNewCard(user.getEmail()))
                creditCard.setActive(true);
            creditCard.setAppUser(user);
            return creditCardRepo.save(creditCard);

    }

    private boolean isNewCard(String email) {
        return !creditCardRepo.existsByAppUser_Email(email);

    }

    @Override
    @Transactional
    public void activateCreditCard(Long id) {
        creditCardRepo.findCurrentActivatedCard()
                .ifPresent(currentActiveCard-> creditCardRepo.updateActiveCard(currentActiveCard.getId(),false));
        creditCardRepo.updateActiveCard(id,true);
    }

    @Override
    public List<CreditCard> getAuthenticatedUserCreditCards() {
        var user = authService.getAuthenticatedUser();
        return creditCardRepo.findAllByAppUser_Email(user.getEmail());
    }

    @Override
    public void update(CreditCard creditCard, Long id) {
                this.creditCardRepo.update(creditCard.getCardType(),creditCard.getCardNumber(),creditCard.getExpirationDate(),id);
    }


    @Override
    public Optional<CreditCard> getByCardNumber(String cardNumber) {
        return creditCardRepo.findByCardNumber(cardNumber);
    }

    @Override
    public void removeById(Long id) {
     creditCardRepo.deleteById(id);
    }
}
