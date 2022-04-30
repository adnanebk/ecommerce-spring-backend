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

import java.util.List;

@Service
@AllArgsConstructor
public class UserOrderServiceImp implements UserOderService {
    private OrderRepository orderRepository;
    private UserRepo userRepo;
    private OrderItemRepo orderItemRepo;
    private CreditCardRepo creditCardRepo;


    @Override
    @Transactional
    public UserOrder saveOrder(UserOrder userOrder, String email) {
        AppUser appUser = userRepo.findByEmail(email).orElseThrow();
        CreditCard creditCard = userOrder.getCreditCard();

        creditCard.setAppUser(appUser);
        if(creditCard.getId()==null)
            creditCard = creditCardRepo.save(creditCard);
        else
            creditCard = creditCardRepo.findById(creditCard.getId()).orElseThrow();


        userOrder.setAppUser(appUser);
        userOrder.setCreditCard(creditCard);
        userOrder.setUserOrderItems(orderItemRepo.saveAll(userOrder.getOrderItems()));
        return orderRepository.save(userOrder);
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
