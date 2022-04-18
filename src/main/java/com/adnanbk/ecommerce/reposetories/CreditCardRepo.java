package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.CreditCard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface CreditCardRepo extends CrudRepository<CreditCard, Long> {



    Optional<CreditCard> findByCardNumber(String cardNumber);



    boolean existsByAppUser_Email(String email);

    @PreAuthorize("#email == authentication.getName()")
    @RestResource(path = "email")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<CreditCard> findAllByAppUser_Email(String email);

    Optional<CreditCard> findByActive(boolean active);

    default Optional<CreditCard> findCurrentActivatedCard() {
        return findByActive(true);
    }

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE CreditCard c SET c.active = :active WHERE c.id= :id")
    void updateActiveCard(Long id, boolean active);
}
