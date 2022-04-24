package com.adnanbk.ecommerce.models;

import com.adnanbk.ecommerce.validations.uniqueEmail.UniqueEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @UniqueEmail
    @Email
    @NotEmpty
    private String email;

    @OneToMany(mappedBy = "appUser", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany(mappedBy = "appUser", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserOrder> userOrders = new HashSet<>();

    @Column
    @NotEmpty
    private String firstName;

    @Column
    @Length(min = 1, message = "{error.min}")
    private String lastName;

    @Column
    @Length(min = 4, message = "{error.min}")
    private String street;

    @Length(min = 2, message = "{error.min}")
    private String city;
    @Length(min = 2, message = "{error.min}")
    private String country;
    private boolean enabled=false;

    private String imageUrl;

    private boolean isSocial;

    @Column
    @NotEmpty
    @Length(min = 4, message = "{error.min}")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles=new HashSet<>();

    public AppUser( String email, String firstName, String lastName,String imageUrl, String password,boolean enabled,boolean isSocial) {
        this.email = email;
        this.imageUrl=imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.enabled=enabled;
        this.isSocial=isSocial;
    }


}
