package com.adnanbk.ecommerce.services;

import com.adnanbk.ecommerce.models.UserOrder;

public interface UserOderService {

    UserOrder saveOrder(UserOrder userOrder, String userName);

}
