package com.adnanbk.ecommerceang.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class ConfirmationToken {

    @Column(name="confirmation_token")
    @Id
    private  String confirmationToken;

   /* @CreationTimestamp
    private Date createdDate;*/
    private LocalDateTime expirationDate;

    @OneToOne(targetEntity = AppUser.class)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser appUser;

    public ConfirmationToken(AppUser user) {
        this.appUser = user;
        confirmationToken = UUID.randomUUID().toString();
        expirationDate=LocalDateTime.now().plusDays(1);
    }
}
