package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.CreditCard;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface CreditCardRepo extends CrudRepository<CreditCard, Long> {


    Iterable<CreditCard> findAllByOrderByActiveDesc();

    Optional<CreditCard> findByCardNumber(String cardNumber);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @RestResource(path = "byUserName")
    @PreAuthorize("#userName == authentication.name")
    Iterable<CreditCard> findByAppUser_UserNameOrderByActiveDesc(String userName);


    boolean existsByAppUser_UserName(String userName);

    Optional<CreditCard> findByActive(boolean active);

    default Optional<CreditCard> findCurrentActivatedCard() {
        return findByActive(true);
    }
}
