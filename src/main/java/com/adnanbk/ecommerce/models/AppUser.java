package com.adnanbk.ecommerce.models;

import com.adnanbk.ecommerce.validations.ConfirmPassword;
import com.adnanbk.ecommerce.validations.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@ConfirmPassword
@NoArgsConstructor
@JsonIgnoreProperties(value = "password", allowSetters = true)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "user_name")
    private String userName;

    @Column(unique = true)
    @UniqueEmail
    @Email
    @NotEmpty
    private String email;

    @OneToMany(mappedBy = "appUser", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany(mappedBy = "appUser", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserOrder> userOrders = new HashSet<>();

    @Column
    @NotEmpty
    private String firstName;

    @Column
    @NotEmpty
    private String lastName;

    @Column
    private String street;

    private String city;
    private String country;
    private boolean enabled;
    
    private boolean isSocial;

    @Column
    @NotEmpty
    @Length(min = 4, message = "{error.min}")
    private String password;

    @Length(min = 4, message = "{error.min}")
    @Transient
    private String confirmPassword;

    public AppUser(String userName, String email, String firstName, String lastName, String password) {
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = password;
    }


}
