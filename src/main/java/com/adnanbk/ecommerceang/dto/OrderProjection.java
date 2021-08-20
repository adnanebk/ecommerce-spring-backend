package com.adnanbk.ecommerceang.dto;

import com.adnanbk.ecommerceang.models.OrderItem;
import com.adnanbk.ecommerceang.models.UserOrder;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.List;

@Projection(name = "userOrder", types =  UserOrder.class)
public interface OrderProjection {

    public String getFullName();

    public String getCountry();

    public String getStreet();

    public String getCity();

    public int getQuantity();

    public double getTotalPrice() ;

    public LocalDate getDateCreated();

    public List<OrderItem> getOrderItems();

}
