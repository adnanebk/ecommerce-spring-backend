package com.adnanbk.ecommerce.models;

import com.adnanbk.ecommerce.validations.cardExpirationDate.FutureExpirationDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "credit_card", uniqueConstraints = {@UniqueConstraint(columnNames = "card_number", name = "uniqueCardNumber")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CreditCard {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{error.empty}")
    @Pattern(regexp = "VISA|MASTERCARD")
    private String cardType;

    private Boolean active;

    @NotNull(message = "{error.empty}")
    @Pattern(regexp = "^(?:(?<visa>[0-9]{12}(?:[0-9]{3})?)|(?<mastercard>[0-9]{16}))$"
            , message = "{error.regExp}")
    @Column(name = "card_number")
    private String cardNumber;

    @NotEmpty(message = "{error.empty}")
    @FutureExpirationDate
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$"
            , message = "{error.regExp}")
    private String expirationDate;

    @OneToMany(cascade = CascadeType.ALL
            , mappedBy = "creditCard"
    )
    @JsonIgnore
    private List<UserOrder> userOrders;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private AppUser appUser;

}
