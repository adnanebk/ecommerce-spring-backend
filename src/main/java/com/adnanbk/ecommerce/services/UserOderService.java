package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.UserOrder;

import java.util.List;

public interface UserOderService {

    UserOrder saveOrder(UserOrder userOrder);

    List<UserOrder> findByEmail(String email);

    void remove(long id);
}
