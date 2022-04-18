package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.UserOrder;

import java.util.List;

public interface UserOderService {

    UserOrder saveOrder(UserOrder userOrder, String email);

    List<UserOrder> findByEmail(String email);

}
