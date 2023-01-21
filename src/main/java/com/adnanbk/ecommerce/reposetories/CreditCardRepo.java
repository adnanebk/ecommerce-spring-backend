package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.CreditCard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditCardRepo extends CrudRepository<CreditCard, Long> {


    @PreAuthorize("#email == authentication.getName()")
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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE CreditCard c SET c.cardType = :cardType,c.cardNumber = :cardNumber,c.expirationDate = :expirationDate WHERE c.id= :id")
    void update(String cardType, String cardNumber, LocalDate expirationDate, Long id);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<CreditCard> findByCardNumber(String cardNumber);

    boolean existsByAppUser_Email(String email);
}
