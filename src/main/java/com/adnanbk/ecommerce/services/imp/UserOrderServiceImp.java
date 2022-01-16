package com.adnanbk.ecommerce.services.imp;

import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.models.CreditCard;
import com.adnanbk.ecommerce.models.UserOrder;
import com.adnanbk.ecommerce.reposetories.CreditCardRepo;
import com.adnanbk.ecommerce.reposetories.OrderItemRepo;
import com.adnanbk.ecommerce.reposetories.OrderRepository;
import com.adnanbk.ecommerce.reposetories.UserRepo;
import com.adnanbk.ecommerce.services.UserOderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserOrderServiceImp implements UserOderService {
    private OrderRepository orderRepository;
    private UserRepo userRepo;
    private OrderItemRepo orderItemRepo;
    private CreditCardRepo creditCardRepo;


    @Override
    @Transactional
    public UserOrder saveOrder(UserOrder userOrder, String userName) {
        AppUser appUser = userRepo.findByUserName(userName);
        CreditCard creditCard = userOrder.getCreditCard();

        var userCardOptional = creditCardRepo.findByCardNumber(creditCard.getCardNumber());
        creditCard.setAppUser(appUser);
        creditCard = userCardOptional.orElse(creditCardRepo.save(creditCard));

        userOrder.setAppUser(appUser);
        userOrder.setCreditCard(creditCard);
        userOrder.setUserOrderItems(orderItemRepo.saveAll(userOrder.getOrderItems()));
        return orderRepository.save(userOrder);
    }

}
