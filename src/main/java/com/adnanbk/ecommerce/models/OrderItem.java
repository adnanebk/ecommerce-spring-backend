package com.adnanbk.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String name;

    private Long productId;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "image")
    private String image;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore // you have to do this to avoid circular relationship
    private UserOrder userOrder;

    private int quantity;

}
