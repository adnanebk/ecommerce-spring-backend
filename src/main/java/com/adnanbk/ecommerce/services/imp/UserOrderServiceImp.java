package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.models.UserOrder;
import com.adnanbk.ecommerce.reposetories.OrderItemRepo;
import com.adnanbk.ecommerce.reposetories.OrderRepository;
import com.adnanbk.ecommerce.services.AuthService;
import com.adnanbk.ecommerce.services.CreditCardService;
import com.adnanbk.ecommerce.services.UserOderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserOrderServiceImp implements UserOderService {
    private OrderRepository orderRepository;
    private AuthService authService;
    private OrderItemRepo orderItemRepo;
    private CreditCardService creditCardService;


    @Override
    @Transactional
    public UserOrder saveOrder(UserOrder userOrder) {
        var appUser = authService.getAuthenticatedUser();
        var creditCard = userOrder.getCreditCard();
            creditCard.setAppUser(appUser);
        userOrder.setCreditCard(getOrCreateCreditCardIfNotExist(creditCard));
        userOrder.setAppUser(appUser);
        userOrder.setUserOrderItems(orderItemRepo.saveAll(userOrder.getOrderItems()));
        return orderRepository.save(userOrder);
    }

    private CreditCard getOrCreateCreditCardIfNotExist(CreditCard creditCard) {
        return creditCardService.getByCardNumber(creditCard.getCardNumber())
                        .orElseGet(()->{
                            creditCard.setId(null);
                            return creditCardService.saveCard(creditCard);
                        });
    }

    @Override
    public List<UserOrder> findByEmail(String email) {
        return orderRepository.findByAppUser_Email(email);
    }

    @Override
    public void remove(long id) {
     this.orderRepository.deleteById(id);
    }


}
