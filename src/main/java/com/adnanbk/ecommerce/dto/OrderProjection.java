package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.models.OrderItem;
import com.adnanbk.ecommerce.models.UserOrder;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.List;

@Projection(name = "userOrder", types = UserOrder.class)
public interface OrderProjection {

    String getFullName();

    String getCountry();

    String getStreet();

    String getCity();

    int getQuantity();

    double getTotalPrice();

    LocalDate getDateCreated();

    List<OrderItem> getOrderItems();

}
