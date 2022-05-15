package com.adnanbk.ecommerce.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "credit_card", uniqueConstraints = {@UniqueConstraint(columnNames = "card_number", name = "uniqueCardNumber")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CreditCard implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardType;

    private Boolean active;

    @Column(name = "card_number")
    private String cardNumber;



    private LocalDate expirationDate;

    @OneToMany(cascade = CascadeType.ALL
            , mappedBy = "creditCard"
    )
    private List<UserOrder> userOrders;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

}
