package com.adnanbk.ecommerce.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    private String token;


    private LocalDateTime expirationDate;

    @OneToOne(targetEntity = AppUser.class)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser appUser;

    public ConfirmationToken(AppUser user) {
        this.appUser = user;
        token = UUID.randomUUID().toString();
        expirationDate = LocalDateTime.now().plusDays(1);
    }
}
