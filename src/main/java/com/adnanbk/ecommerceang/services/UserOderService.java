package com.adnanbk.ecommerceang.services;

import com.adnanbk.ecommerceang.models.UserOrder;

public interface UserOderService {

    UserOrder saveOrder(UserOrder userOrder, String userName);

}
