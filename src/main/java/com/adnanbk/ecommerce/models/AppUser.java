package com.adnanbk.ecommerce.models;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "uniqueEmail")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AppUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @OneToMany(mappedBy = "appUser", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany(mappedBy = "appUser", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserOrder> userOrders = new HashSet<>();

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String street;

    private String city;
    private String country;
    private boolean enabled=false;
    private boolean isSocial;

    private String imageUrl;


    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles=new HashSet<>();




}
