package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.models.UserOrder;
import com.adnanbk.ecommerce.reposetories.OrderItemRepo;
import com.adnanbk.ecommerce.reposetories.OrderRepository;
import com.adnanbk.ecommerce.services.CreditCardService;
import com.adnanbk.ecommerce.services.UserOderService;
import com.adnanbk.ecommerce.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserOrderServiceImp implements UserOderService {
    private OrderRepository orderRepository;
    private UserService userService;
    private OrderItemRepo orderItemRepo;
    private CreditCardService creditCardService;


    @Override
    @Transactional
    public UserOrder saveOrder(UserOrder userOrder, String email) {
        var appUser = userService.getUserByEmail(email);
        var creditCard = userOrder.getCreditCard();
         creditCard.setAppUser(appUser);
         userOrder.setCreditCard(getOrCreateCreditCardIfNotExist(creditCard,email));
        userOrder.setAppUser(appUser);
        userOrder.setUserOrderItems(orderItemRepo.saveAll(userOrder.getOrderItems()));
        return orderRepository.save(userOrder);
    }

    private CreditCard getOrCreateCreditCardIfNotExist(CreditCard creditCard, String email) {
        return Optional.ofNullable(creditCard.getCardNumber())
                .map(cardNumber -> creditCardService.getByCardNumber(cardNumber)
                        .orElseGet(()->creditCardService.saveCard(creditCard,email))).orElseThrow();
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
