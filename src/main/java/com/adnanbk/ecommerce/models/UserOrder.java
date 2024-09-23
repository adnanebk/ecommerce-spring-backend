package com.adnanbk.ecommerce.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String fullName;


    private String country;


    private String street;


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
        this.orderItems.clear();
        this.orderItems.addAll(orderItems);
        orderItems.forEach(orderItem -> orderItem.setUserOrder(this));
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    @NotNull
    private CreditCard creditCard;


}

