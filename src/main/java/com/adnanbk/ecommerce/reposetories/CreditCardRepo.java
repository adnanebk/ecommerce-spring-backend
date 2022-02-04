package com.adnanbk.ecommerce.reposetories;

import com.adnanbk.ecommerce.models.CreditCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface CreditCardRepo extends CrudRepository<CreditCard, Long> {

    @Override
    @RestResource(exported = false)
    Iterable<CreditCard> findAll();

    @RestResource(exported = false)
    Iterable<CreditCard> findAllOrderByActiveDesc();

    @Override
    @RestResource(exported = false)
    Optional<CreditCard> findById(Long aLong);

    @Override
    @RestResource(exported = false)
    Iterable<CreditCard> findAllById(Iterable<Long> iterable);

    Optional<CreditCard> findByCardNumber(String cardNumber);

    @RestResource(path = "byUserName")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @PreAuthorize("#userName == authentication.name")
    List<CreditCard> findByAppUser_UserNameOrderByActiveDesc(String userName);


    boolean existsByAppUser_UserName(String userName);

    @Override
    <S extends CreditCard> S save(S s);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(CreditCard creditCard);

     Optional<CreditCard> findByActive(boolean active);

    default Optional<CreditCard> findCurrentActivatedCard(){
        return findByActive(true);
    }
}
