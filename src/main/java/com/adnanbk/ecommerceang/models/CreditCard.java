package com.adnanbk.ecommerceang.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Data
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotEmpty(message = "{error.empty}")
    @Pattern(regexp = "VISA|MASTERCARD")
    private String cardType;

    private Boolean active;

    @NotNull(message = "{error.empty}")
    @Pattern(regexp = "^(?:(?<visa>[0-9]{12}(?:[0-9]{3})?)|(?<mastercard>[0-9]{14}))$"
              ,message = "{error.regExp}")
    @Column(name = "card_name",unique = true)
    private String cardNumber;

    @NotEmpty
    @Pattern(regexp = "^\\d{2}\\/\\d{2}$",message = "{error.regExp}")
    private String expirationDate;

    @OneToMany(cascade = CascadeType.ALL
            ,mappedBy = "creditCard"
    )
    @JsonIgnore
    private List<UserOrder> userOrders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AppUser appUser;

}
