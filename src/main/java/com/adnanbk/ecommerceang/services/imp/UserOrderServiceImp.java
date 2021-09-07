package com.adnanbk.ecommerceang.services.imp;

import com.adnanbk.ecommerceang.models.AppUser;
import com.adnanbk.ecommerceang.models.CreditCard;
import com.adnanbk.ecommerceang.models.UserOrder;
import com.adnanbk.ecommerceang.reposetories.CreditCardRepo;
import com.adnanbk.ecommerceang.reposetories.OrderItemRepo;
import com.adnanbk.ecommerceang.reposetories.OrderRepository;
import com.adnanbk.ecommerceang.reposetories.UserRepo;
import com.adnanbk.ecommerceang.services.UserOderService;
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
        AppUser appUser =userRepo.findByUserName(userName);
        CreditCard creditCard=userOrder.getCreditCard();

            var userCardOptional=creditCardRepo.findByCardNumber(creditCard.getCardNumber());
            creditCard.setAppUser(appUser);
            creditCard=userCardOptional.orElse(creditCardRepo.save(creditCard));

        userOrder.setAppUser(appUser);
        userOrder.setCreditCard(creditCard);
        userOrder.setUserOrderItems(orderItemRepo.saveAll(userOrder.getOrderItems()));
      return  orderRepository.save(userOrder);
    }

}
