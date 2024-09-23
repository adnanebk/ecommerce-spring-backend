package com.adnanbk.ecommerce.dto;


import com.adnanbk.ecommerce.models.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserOrderDto {

    private Long id;

    @NotEmpty
    @Length(min = 4, message = "{error.min}")
    private String fullName;


    @NotEmpty
    private String country;

    @NotEmpty
    @Length(min = 4, message = "{error.min}")
    private String street;

    @NotEmpty
    private String city;

    private int quantity;
    private double totalPrice;


    private LocalDate dateCreated = LocalDate.now();

    private List<OrderItem> orderItems = new ArrayList<>();

    @NotNull
    @Valid
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CreditCardDto creditCard;


}

