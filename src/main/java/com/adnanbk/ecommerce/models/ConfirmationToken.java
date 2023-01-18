package com.adnanbk.ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Future;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    private String token;

    @Future
    private LocalDate expirationDate;

    @OneToOne(targetEntity = AppUser.class)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser appUser;

}
