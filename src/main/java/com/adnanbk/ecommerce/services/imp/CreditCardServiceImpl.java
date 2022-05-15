package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.reposetories.CreditCardRepo;
import com.adnanbk.ecommerce.services.CreditCardService;
import com.adnanbk.ecommerce.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepo creditCardRepo;
    private UserService userService;



    @Override
    @Transactional
    public CreditCard saveCard(CreditCard creditCard, String email) {
       var user =   userService.getUserByEmail(email);
            activeCreditCardIfNew(creditCard, email);
            creditCard.setAppUser(user);
            // to make sure not updated or create a dto
            creditCard.setId(null);
            return creditCardRepo.save(creditCard);

    }

    private void activeCreditCardIfNew(CreditCard creditCard, String email) {
        if (creditCardRepo.findAllByAppUser_Email(email).isEmpty())
            creditCard.setActive(true);
    }

    @Override
    @Transactional
    public void activateCreditCard(Long id) {
        var currentActiveCard = creditCardRepo.findCurrentActivatedCard().orElseThrow();
        creditCardRepo.updateActiveCard(currentActiveCard.getId(),false);
        creditCardRepo.updateActiveCard(id,true);
    }

    @Override
    public List<CreditCard> getByEmail(String email) {
        return creditCardRepo.findAllByAppUser_Email(email);
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
