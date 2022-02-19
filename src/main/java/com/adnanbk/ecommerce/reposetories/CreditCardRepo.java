package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.CreditCard;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface CreditCardRepo extends CrudRepository<CreditCard, Long> {


    Iterable<CreditCard> findAllByOrderByActiveDesc();

    Optional<CreditCard> findByCardNumber(String cardNumber);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    //@PreAuthorize("#userName == authentication.name")
    List<CreditCard> findByAppUser_UserNameOrderByActiveDesc(String userName);


    boolean existsByAppUser_UserName(String userName);

     Optional<CreditCard> findByActive(boolean active);

    default Optional<CreditCard> findCurrentActivatedCard(){
        return findByActive(true);
    }
}
