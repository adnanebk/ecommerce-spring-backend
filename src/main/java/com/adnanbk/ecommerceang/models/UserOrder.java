package com.adnanbk.ecommerceang.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"creditCard"}, allowSetters = true)
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDate dateCreated;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true
            , mappedBy = "userOrder"
    )
    private List<OrderItem> orderItems;

    public void setUserOrderItems(List<OrderItem> orderItems) {
        //orderItems.forEach(e->e.setUserOrder(this));
        this.orderItems.clear();
        this.orderItems.addAll(orderItems);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    @NotNull
    private CreditCard creditCard;
/*    private String country;

    private String city;


    private String street;*/


 /*   @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Product> products;*/


}

